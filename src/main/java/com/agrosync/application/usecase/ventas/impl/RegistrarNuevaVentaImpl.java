package com.agrosync.application.usecase.ventas.impl;

import com.agrosync.application.primaryports.enums.animales.EstadoAnimalEnum;
import com.agrosync.application.primaryports.enums.cuentas.EstadoCuentaEnum;
import com.agrosync.application.primaryports.enums.cuentas.MetodoPagoEnum;
import com.agrosync.application.primaryports.enums.usuarios.TipoUsuarioEnum;
import com.agrosync.application.secondaryports.entity.abonos.AbonoEntity;
import com.agrosync.application.secondaryports.entity.animales.AnimalEntity;
import com.agrosync.application.secondaryports.entity.carteras.CarteraEntity;
import com.agrosync.application.secondaryports.entity.cuentascobrar.CuentaCobrarEntity;
import com.agrosync.application.secondaryports.entity.cuentaspagar.CuentaPagarEntity;
import com.agrosync.application.secondaryports.entity.suscripcion.SuscripcionEntity;
import com.agrosync.application.secondaryports.entity.usuarios.UsuarioEntity;
import com.agrosync.application.secondaryports.entity.ventas.VentaEntity;
import com.agrosync.application.secondaryports.mapper.ventas.VentaEntityMapper;
import com.agrosync.application.secondaryports.repository.AbonoRepository;
import com.agrosync.application.secondaryports.repository.AnimalRepository;
import com.agrosync.application.secondaryports.repository.CarteraRepository;
import com.agrosync.application.secondaryports.repository.CuentaPagarRepository;
import com.agrosync.application.secondaryports.repository.UsuarioRepository;
import com.agrosync.application.secondaryports.repository.VentaRepository;
import com.agrosync.application.usecase.ventas.RegistrarNuevaVenta;
import com.agrosync.application.usecase.ventas.rulesvalidator.RegistrarNuevaVentaRulesValidator;
import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.crosscutting.helpers.TextHelper;
import com.agrosync.domain.animales.exceptions.AnimalNoDisponibleParaVentaException;
import com.agrosync.domain.animales.exceptions.IdentificadorAnimalNoExisteException;
import com.agrosync.domain.animales.AnimalDomain;
import com.agrosync.domain.ventas.VentaDomain;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class RegistrarNuevaVentaImpl implements RegistrarNuevaVenta {

    private final VentaRepository ventaRepository;
    private final AnimalRepository animalRepository;
    private final CarteraRepository carteraRepository;
    private final UsuarioRepository usuarioRepository;
    private final CuentaPagarRepository cuentaPagarRepository;
    private final AbonoRepository abonoRepository;
    private final RegistrarNuevaVentaRulesValidator registrarNuevaVentaRulesValidator;
    private final VentaEntityMapper ventaEntityMapper;

    public RegistrarNuevaVentaImpl(VentaRepository ventaRepository,
                                   AnimalRepository animalRepository,
                                   CarteraRepository carteraRepository,
                                   UsuarioRepository usuarioRepository,
                                   CuentaPagarRepository cuentaPagarRepository,
                                   AbonoRepository abonoRepository,
                                   RegistrarNuevaVentaRulesValidator registrarNuevaVentaRulesValidator,
                                   VentaEntityMapper ventaEntityMapper) {
        this.ventaRepository = ventaRepository;
        this.animalRepository = animalRepository;
        this.carteraRepository = carteraRepository;
        this.usuarioRepository = usuarioRepository;
        this.cuentaPagarRepository = cuentaPagarRepository;
        this.abonoRepository = abonoRepository;
        this.registrarNuevaVentaRulesValidator = registrarNuevaVentaRulesValidator;
        this.ventaEntityMapper = ventaEntityMapper;
    }

    @Override
    public void ejecutar(VentaDomain data) {
        registrarNuevaVentaRulesValidator.validar(data);

        VentaEntity venta = ventaEntityMapper.toEntity(data);
        SuscripcionEntity suscripcion = SuscripcionEntity.create(data.getSuscripcionId());
        venta.setSuscripcion(suscripcion);

        LocalDate fechaVenta = ObjectHelper.getDefault(data.getFechaVenta(), LocalDate.now());
        venta.setFechaVenta(fechaVenta);

        if (TextHelper.isEmpty(venta.getNumeroVenta())) {
            venta.setNumeroVenta(generarNumeroVenta());
        }

        List<AnimalEntity> animalesVendidos = obtenerAnimalesParaVenta(data.getAnimales(), suscripcion);
        venta.setAnimales(animalesVendidos);

        BigDecimal precioTotalVenta = calcularPrecioTotal(animalesVendidos);
        venta.setPrecioTotalVenta(precioTotalVenta);

        CuentaCobrarEntity cuentaCobrar = ObjectHelper.getDefault(venta.getCuentaCobrar(), new CuentaCobrarEntity());
        venta.setCuentaCobrar(cuentaCobrar);
        cuentaCobrar.setVenta(venta);
        UsuarioEntity cliente = ObjectHelper.isNull(venta.getCliente())
                ? UsuarioEntity.create(data.getCliente().getId())
                : venta.getCliente();
        cuentaCobrar.setCliente(cliente);
        cuentaCobrar.setSuscripcion(suscripcion);
        cuentaCobrar.setMontoTotal(precioTotalVenta);
        cuentaCobrar.setSaldoPendiente(precioTotalVenta);
        cuentaCobrar.setEstado(EstadoCuentaEnum.PENDIENTE);
        cuentaCobrar.setFechaEmision(fechaVenta);
        cuentaCobrar.setFechaVencimiento(fechaVenta);
        if (TextHelper.isEmpty(cuentaCobrar.getNumeroCuenta())) {
            cuentaCobrar.setNumeroCuenta(generarNumeroCuenta(venta.getNumeroVenta()));
        }

        VentaEntity ventaGuardada = ventaRepository.save(venta);
        asociarAnimalesAVenta(ventaGuardada, animalesVendidos, suscripcion);

        // Obtener el usuario cliente completo
        UsuarioEntity clienteCompleto = usuarioRepository.findByIdAndSuscripcion_Id(
                cliente.getId(), suscripcion.getId()
        ).orElse(null);

        // Si el cliente es tipo AMBOS, verificar si tiene cuentas por pagar pendientes
        // (cuentas donde le debemos a nosotros)
        BigDecimal montoCompensado = BigDecimal.ZERO;
        if (clienteCompleto != null && clienteCompleto.getTipoUsuario() == TipoUsuarioEnum.AMBOS) {
            montoCompensado = compensarConCuentasPagar(clienteCompleto, suscripcion, precioTotalVenta, fechaVenta);
        }

        // Actualizar la cartera del cliente con el monto neto
        BigDecimal montoNeto = precioTotalVenta.subtract(montoCompensado);
        if (montoNeto.compareTo(BigDecimal.ZERO) > 0) {
            actualizarCarteraCliente(cliente.getId(), suscripcion.getId(), montoNeto);
        }
    }

    /**
     * Compensa el monto de la venta con las cuentas por pagar pendientes del cliente
     * (cuando el cliente también es proveedor y le debemos dinero)
     */
    private BigDecimal compensarConCuentasPagar(UsuarioEntity cliente, SuscripcionEntity suscripcion,
                                                 BigDecimal montoVenta, LocalDate fechaVenta) {
        // Buscar cuentas por pagar pendientes donde el cliente es el proveedor (le debemos)
        List<CuentaPagarEntity> cuentasPendientes = cuentaPagarRepository
                .findByProveedor_IdAndSuscripcion_IdAndEstadoNot(
                        cliente.getId(),
                        suscripcion.getId(),
                        EstadoCuentaEnum.PAGADA
                );

        BigDecimal montoDisponible = montoVenta;
        BigDecimal totalCompensado = BigDecimal.ZERO;

        for (CuentaPagarEntity cuenta : cuentasPendientes) {
            if (montoDisponible.compareTo(BigDecimal.ZERO) <= 0) {
                break;
            }

            BigDecimal saldoPendiente = cuenta.getSaldoPendiente();
            BigDecimal montoAAbonar = montoDisponible.min(saldoPendiente);

            // Crear el abono automático
            AbonoEntity abono = new AbonoEntity();
            abono.setCuentaPagar(cuenta);
            abono.setMonto(montoAAbonar);
            abono.setFechaPago(fechaVenta.atStartOfDay());
            abono.setMetodoPago(MetodoPagoEnum.OTRO);
            abono.setConcepto("Compensación automática por venta - Cruce de cuentas");
            abono.setSuscripcion(suscripcion);
            abonoRepository.save(abono);

            // Actualizar el saldo de la cuenta por pagar
            BigDecimal nuevoSaldo = saldoPendiente.subtract(montoAAbonar);
            cuenta.setSaldoPendiente(nuevoSaldo);

            if (nuevoSaldo.compareTo(BigDecimal.ZERO) == 0) {
                cuenta.setEstado(EstadoCuentaEnum.PAGADA);
            } else {
                cuenta.setEstado(EstadoCuentaEnum.PARCIALMENTE_PAGADA);
            }
            cuentaPagarRepository.save(cuenta);

            // Actualizar cartera del cliente (reducir lo que le debíamos)
            actualizarCarteraPorAbono(cliente, montoAAbonar);

            montoDisponible = montoDisponible.subtract(montoAAbonar);
            totalCompensado = totalCompensado.add(montoAAbonar);
        }

        return totalCompensado;
    }

    private void actualizarCarteraPorAbono(UsuarioEntity usuario, BigDecimal montoAbono) {
        CarteraEntity cartera = usuario.getCartera();
        if (cartera != null) {
            // Reducir cuentas por cobrar del usuario (le pagamos, ya no le debemos tanto)
            BigDecimal nuevoTotalCuentasCobrar = cartera.getTotalCuentasCobrar().subtract(montoAbono);
            cartera.setTotalCuentasCobrar(nuevoTotalCuentasCobrar);

            // Reducir saldo del usuario (le debemos menos)
            BigDecimal saldoActual = cartera.getSaldoActual().subtract(montoAbono);
            cartera.setSaldoActual(saldoActual);

            carteraRepository.save(cartera);
        }
    }

    private void actualizarCarteraCliente(UUID clienteId, UUID suscripcionId, BigDecimal montoVenta) {
        Optional<CarteraEntity> carteraOpt = carteraRepository.findByUsuario_IdAndSuscripcion_Id(clienteId, suscripcionId);
        if (carteraOpt.isPresent()) {
            CarteraEntity cartera = carteraOpt.get();
            // Aumentar cuentas por pagar del cliente (él nos debe a nosotros)
            BigDecimal totalActual = ObjectHelper.getDefault(cartera.getTotalCuentasPagar(), BigDecimal.ZERO);
            cartera.setTotalCuentasPagar(totalActual.add(montoVenta));

            // Saldo negativo para el cliente (él nos debe dinero)
            BigDecimal saldoActual = ObjectHelper.getDefault(cartera.getSaldoActual(), BigDecimal.ZERO);
            cartera.setSaldoActual(saldoActual.subtract(montoVenta));

            carteraRepository.save(cartera);
        }
    }

    private void asociarAnimalesAVenta(VentaEntity venta, List<AnimalEntity> animalesVendidos, SuscripcionEntity suscripcion) {
        if (ObjectHelper.isNull(animalesVendidos)) {
            return;
        }

        animalesVendidos.forEach(animal -> {
            animal.setVenta(venta);
            animal.setSuscripcion(suscripcion);
        });

        animalRepository.saveAll(animalesVendidos);
    }

    private List<AnimalEntity> obtenerAnimalesParaVenta(List<AnimalDomain> animales, SuscripcionEntity suscripcion) {
        List<AnimalEntity> animalesVendidos = new ArrayList<>();
        if (ObjectHelper.isNull(animales)) {
            return animalesVendidos;
        }

        for (AnimalDomain animalDomain : animales) {
            UUID animalId = animalDomain.getId();
            AnimalEntity animal = animalRepository.findByIdAndSuscripcion_Id(animalId, suscripcion.getId())
                    .orElseThrow(IdentificadorAnimalNoExisteException::create);

            if (animal.getEstado() != EstadoAnimalEnum.DISPONIBLE) {
                throw AnimalNoDisponibleParaVentaException.create();
            }

            BigDecimal precioKiloVenta = ObjectHelper.getDefault(animalDomain.getPrecioKiloVenta(), BigDecimal.ZERO);
            animal.setPrecioKiloVenta(precioKiloVenta);
            animal.setEstado(EstadoAnimalEnum.VENDIDO);
            animalesVendidos.add(animal);
        }

        return animalesVendidos;
    }

    private BigDecimal calcularPrecioTotal(List<AnimalEntity> animalesVendidos) {
        BigDecimal precioTotal = BigDecimal.ZERO;
        for (AnimalEntity animal : animalesVendidos) {
            BigDecimal peso = ObjectHelper.getDefault(animal.getPeso(), BigDecimal.ZERO);
            BigDecimal precioKiloVenta = ObjectHelper.getDefault(animal.getPrecioKiloVenta(), BigDecimal.ZERO);
            precioTotal = precioTotal.add(peso.multiply(precioKiloVenta));
        }
        return precioTotal;
    }

    private String generarNumeroVenta() {
        return String.format("VE-%s", generarDigitosAleatorios(4));
    }

    private String generarNumeroCuenta(String numeroVenta) {
        return String.format("CXC-%s", TextHelper.getDefault(numeroVenta, TextHelper.EMPTY));
    }

    private String generarDigitosAleatorios(int cantidad) {
        int limite = (int) Math.pow(10, cantidad);
        int numero = ThreadLocalRandom.current().nextInt(limite);
        return String.format("%0" + cantidad + "d", numero);
    }
}
