package com.agrosync.application.usecase.compras.impl;

import com.agrosync.application.primaryports.enums.usuarios.TipoUsuarioEnum;
import com.agrosync.application.secondaryports.entity.carteras.CarteraEntity;
import com.agrosync.application.secondaryports.entity.cobros.CobroEntity;
import com.agrosync.application.secondaryports.entity.compras.CompraEntity;
import com.agrosync.application.secondaryports.entity.animales.AnimalEntity;
import com.agrosync.application.secondaryports.entity.cuentascobrar.CuentaCobrarEntity;
import com.agrosync.application.secondaryports.entity.cuentaspagar.CuentaPagarEntity;
import com.agrosync.application.secondaryports.entity.lotes.LoteEntity;
import com.agrosync.application.secondaryports.entity.suscripcion.SuscripcionEntity;
import com.agrosync.application.secondaryports.entity.usuarios.UsuarioEntity;
import com.agrosync.application.secondaryports.mapper.compras.CompraEntityMapper;
import com.agrosync.application.secondaryports.repository.CarteraRepository;
import com.agrosync.application.secondaryports.repository.CobroRepository;
import com.agrosync.application.secondaryports.repository.CompraRepository;
import com.agrosync.application.secondaryports.repository.CuentaCobrarRepository;
import com.agrosync.application.secondaryports.repository.UsuarioRepository;
import com.agrosync.application.usecase.animales.rulesvalidator.RegistrarNuevoAnimalRulesValidator;
import com.agrosync.application.usecase.compras.RegistrarNuevaCompra;
import com.agrosync.application.primaryports.enums.animales.EstadoAnimalEnum;
import com.agrosync.application.primaryports.enums.cuentas.EstadoCuentaEnum;
import com.agrosync.application.primaryports.enums.cuentas.MetodoPagoEnum;
import com.agrosync.application.usecase.compras.rulesvalidator.RegistrarNuevaCompraRulesValidator;
import com.agrosync.application.usecase.lotes.rulesvalidator.RegistrarNuevoLoteRulesValidator;
import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.crosscutting.helpers.TextHelper;
import com.agrosync.domain.compras.CompraDomain;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class RegistrarNuevaCompraImpl implements RegistrarNuevaCompra {

    private final CompraRepository compraRepository;
    private final CarteraRepository carteraRepository;
    private final UsuarioRepository usuarioRepository;
    private final CuentaCobrarRepository cuentaCobrarRepository;
    private final CobroRepository cobroRepository;
    private final RegistrarNuevaCompraRulesValidator registrarNuevaCompraRulesValidator;
    private final RegistrarNuevoLoteRulesValidator registrarNuevoLoteRulesValidator;
    private final RegistrarNuevoAnimalRulesValidator registrarNuevoAnimalRulesValidator;
    private final CompraEntityMapper compraEntityMapper;

    public RegistrarNuevaCompraImpl(CompraRepository compraRepository,
                                    CarteraRepository carteraRepository,
                                    UsuarioRepository usuarioRepository,
                                    CuentaCobrarRepository cuentaCobrarRepository,
                                    CobroRepository cobroRepository,
                                    RegistrarNuevaCompraRulesValidator registrarNuevaCompraRulesValidator,
                                    RegistrarNuevoLoteRulesValidator registrarNuevoLoteRulesValidator,
                                    RegistrarNuevoAnimalRulesValidator registrarNuevoAnimalRulesValidator,
                                    CompraEntityMapper compraEntityMapper) {
        this.compraRepository = compraRepository;
        this.carteraRepository = carteraRepository;
        this.usuarioRepository = usuarioRepository;
        this.cuentaCobrarRepository = cuentaCobrarRepository;
        this.cobroRepository = cobroRepository;
        this.registrarNuevaCompraRulesValidator = registrarNuevaCompraRulesValidator;
        this.registrarNuevoLoteRulesValidator = registrarNuevoLoteRulesValidator;
        this.registrarNuevoAnimalRulesValidator = registrarNuevoAnimalRulesValidator;
        this.compraEntityMapper = compraEntityMapper;
    }

    @Override
    public void ejecutar(CompraDomain data) {
        // 1. Validar reglas de la compra
        registrarNuevaCompraRulesValidator.validar(data);

        // 2. Preparar datos del lote para validación
        LocalDate fechaCompra = ObjectHelper.getDefault(data.getFechaCompra(), LocalDate.now());
        data.getLote().setFecha(fechaCompra);
        data.getLote().setSuscripcionId(data.getSuscripcionId());

        // 3. Validar reglas del lote
        registrarNuevoLoteRulesValidator.validar(data.getLote());

        // 4. Validar reglas de cada animal
        if (!ObjectHelper.isNull(data.getLote().getAnimales())) {
            for (var animalDomain : data.getLote().getAnimales()) {
                if (!ObjectHelper.isNull(animalDomain)) {
                    animalDomain.setSuscripcionId(data.getSuscripcionId());
                    registrarNuevoAnimalRulesValidator.validar(animalDomain);
                }
            }
        }

        // 5. Mapear a entidad y continuar con el procesamiento
        CompraEntity compra = compraEntityMapper.toEntity(data);

        SuscripcionEntity suscripcion = SuscripcionEntity.create(data.getSuscripcionId());
        compra.setSuscripcion(suscripcion);
        compra.setFechaCompra(fechaCompra);

        LoteEntity lote = compra.getLote();
        lote.setCompra(compra);
        lote.setSuscripcion(suscripcion);
        lote.setFecha(fechaCompra);
        if (TextHelper.isEmpty(lote.getNumeroLote())) {
            lote.setNumeroLote(generarNumeroLote(lote.getContramarca(), lote.getFecha()));
        }

        BigDecimal pesoTotal = BigDecimal.ZERO;
        BigDecimal precioTotalCompra = BigDecimal.ZERO;

        if (!ObjectHelper.isNull(lote.getAnimales())) {
            for (AnimalEntity animal : lote.getAnimales()) {
                if (ObjectHelper.isNull(animal)) {
                    continue;
                }

                animal.setLote(lote);
                animal.setSuscripcion(suscripcion);
                animal.setVenta(null);
                animal.setEstado(EstadoAnimalEnum.DISPONIBLE);

                if (TextHelper.isEmpty(animal.getNumeroAnimal())) {
                    animal.setNumeroAnimal(generarNumeroAnimal(lote.getContramarca(), animal.getSlot(), lote.getFecha()));
                }

                BigDecimal peso = ObjectHelper.getDefault(animal.getPeso(), BigDecimal.ZERO);
                BigDecimal precioKiloCompra = ObjectHelper.getDefault(animal.getPrecioKiloCompra(), BigDecimal.ZERO);

                pesoTotal = pesoTotal.add(peso);
                precioTotalCompra = precioTotalCompra.add(peso.multiply(precioKiloCompra));
            }
        }

        lote.setPesoTotal(pesoTotal);
        compra.setPrecioTotalCompra(precioTotalCompra);

        if (TextHelper.isEmpty(compra.getNumeroCompra())) {
            compra.setNumeroCompra(generarNumeroCompra(lote.getContramarca()));
        }

        CuentaPagarEntity cuentaPagar = compra.getCuentaPagar();
        if (ObjectHelper.isNull(cuentaPagar)) {
            cuentaPagar = new CuentaPagarEntity();
            compra.setCuentaPagar(cuentaPagar);
        }

        cuentaPagar.setCompra(compra);
        cuentaPagar.setProveedor(compra.getProveedor());
        cuentaPagar.setSuscripcion(suscripcion);
        cuentaPagar.setMontoTotal(precioTotalCompra);
        cuentaPagar.setSaldoPendiente(precioTotalCompra);
        cuentaPagar.setEstado(EstadoCuentaEnum.PENDIENTE);
        cuentaPagar.setFechaEmision(fechaCompra);
        cuentaPagar.setFechaVencimiento(fechaCompra);

        if (TextHelper.isEmpty(cuentaPagar.getNumeroCuenta())) {
            cuentaPagar.setNumeroCuenta(generarNumeroCuenta(compra.getNumeroCompra()));
        }

        compraRepository.save(compra);

        // Obtener el usuario proveedor completo
        UsuarioEntity proveedor = usuarioRepository.findByIdAndSuscripcion_Id(
                compra.getProveedor().getId(), suscripcion.getId()
        ).orElse(null);

        // Si el proveedor es tipo AMBOS, verificar si tiene cuentas por cobrar pendientes
        // (cuentas donde él nos debe dinero porque le vendimos antes)
        BigDecimal montoCompensado = BigDecimal.ZERO;
        if (proveedor != null && proveedor.getTipoUsuario() == TipoUsuarioEnum.AMBOS) {
            montoCompensado = compensarConCuentasCobrar(proveedor, suscripcion, precioTotalCompra, fechaCompra);
        }

        // Actualizar la cartera del proveedor con el monto neto
        BigDecimal montoNeto = precioTotalCompra.subtract(montoCompensado);
        if (montoNeto.compareTo(BigDecimal.ZERO) > 0) {
            actualizarCarteraProveedor(compra.getProveedor().getId(), suscripcion.getId(), montoNeto);
        }
    }

    private BigDecimal compensarConCuentasCobrar(UsuarioEntity proveedor, SuscripcionEntity suscripcion,
                                                   BigDecimal montoCompra, LocalDate fechaCompra) {
        // Buscar cuentas por cobrar pendientes donde el proveedor es el cliente (él nos debe)
        List<CuentaCobrarEntity> cuentasPendientes = cuentaCobrarRepository
                .findByCliente_IdAndSuscripcion_IdAndEstadoNot(
                        proveedor.getId(),
                        suscripcion.getId(),
                        EstadoCuentaEnum.COBRADA
                );

        BigDecimal montoDisponible = montoCompra;
        BigDecimal totalCompensado = BigDecimal.ZERO;

        for (CuentaCobrarEntity cuenta : cuentasPendientes) {
            if (montoDisponible.compareTo(BigDecimal.ZERO) <= 0) {
                break;
            }

            BigDecimal saldoPendiente = cuenta.getSaldoPendiente();
            BigDecimal montoACobrar = montoDisponible.min(saldoPendiente);

            // Crear el cobro automático
            CobroEntity cobro = new CobroEntity();
            cobro.setCuentaCobrar(cuenta);
            cobro.setMonto(montoACobrar);
            cobro.setFechaCobro(fechaCompra.atStartOfDay());
            cobro.setMetodoPago(MetodoPagoEnum.OTRO);
            cobro.setConcepto("Compensación automática por compra - Cruce de cuentas");
            cobro.setSuscripcion(suscripcion);
            cobroRepository.save(cobro);

            // Actualizar el saldo de la cuenta por cobrar
            BigDecimal nuevoSaldo = saldoPendiente.subtract(montoACobrar);
            cuenta.setSaldoPendiente(nuevoSaldo);

            if (nuevoSaldo.compareTo(BigDecimal.ZERO) == 0) {
                cuenta.setEstado(EstadoCuentaEnum.COBRADA);
            } else {
                cuenta.setEstado(EstadoCuentaEnum.PARCIALMENTE_COBRADA);
            }
            cuentaCobrarRepository.save(cuenta);

            // Actualizar cartera del proveedor (reducir lo que nos debía)
            actualizarCarteraPorCobro(proveedor, montoACobrar);

            montoDisponible = montoDisponible.subtract(montoACobrar);
            totalCompensado = totalCompensado.add(montoACobrar);
        }

        return totalCompensado;
    }

    private void actualizarCarteraPorCobro(UsuarioEntity usuario, BigDecimal montoCobro) {
        CarteraEntity cartera = usuario.getCartera();
        if (cartera != null) {
            // Reducir cuentas por pagar del usuario (nos pagó, ya no nos debe tanto)
            BigDecimal nuevoTotalCuentasPagar = cartera.getTotalCuentasPagar().subtract(montoCobro);
            cartera.setTotalCuentasPagar(nuevoTotalCuentasPagar);

            // Aumentar saldo del usuario (nos debe menos)
            BigDecimal saldoActual = cartera.getSaldoActual().add(montoCobro);
            cartera.setSaldoActual(saldoActual);

            carteraRepository.save(cartera);
        }
    }

    private void actualizarCarteraProveedor(UUID proveedorId, UUID suscripcionId, BigDecimal montoCompra) {
        Optional<CarteraEntity> carteraOpt = carteraRepository.findByUsuario_IdAndSuscripcion_Id(proveedorId, suscripcionId);
        if (carteraOpt.isPresent()) {
            CarteraEntity cartera = carteraOpt.get();
            // Aumentar cuentas por cobrar del proveedor (nosotros le debemos a él)
            BigDecimal totalActual = ObjectHelper.getDefault(cartera.getTotalCuentasCobrar(), BigDecimal.ZERO);
            cartera.setTotalCuentasCobrar(totalActual.add(montoCompra));

            // Saldo positivo para el proveedor (le debemos dinero)
            BigDecimal saldoActual = ObjectHelper.getDefault(cartera.getSaldoActual(), BigDecimal.ZERO);
            cartera.setSaldoActual(saldoActual.add(montoCompra));

            carteraRepository.save(cartera);
        }
    }

    private String generarNumeroCompra(String contramarca) {
        return String.format("CO-%s-%s", TextHelper.getDefault(contramarca, TextHelper.EMPTY), generarDigitosAleatorios(4));
    }

    private String generarNumeroCuenta(String numeroCompra) {
        return String.format("CXP-%s", TextHelper.getDefault(numeroCompra, TextHelper.EMPTY));
    }

    private String generarNumeroLote(String contramarca, LocalDate fecha) {
        LocalDate fechaLote = ObjectHelper.getDefault(fecha, LocalDate.now());
        int semana = fechaLote.get(WeekFields.ISO.weekOfWeekBasedYear());
        return String.format("%s-%d-%d", TextHelper.getDefault(contramarca, " "), semana, fechaLote.getYear());
    }

    private String generarNumeroAnimal(String contramarca, String slot, LocalDate fecha) {
        LocalDate fechaAnimal = ObjectHelper.getDefault(fecha, LocalDate.now());
        int semana = fechaAnimal.get(WeekFields.ISO.weekOfWeekBasedYear());
        return String.format("%s-%s-%02d-%d", TextHelper.getDefault(contramarca, TextHelper.EMPTY), TextHelper.getDefault(slot, TextHelper.EMPTY), semana, fechaAnimal.getYear());
    }

    private String generarDigitosAleatorios(int cantidad) {
        int limite = (int) Math.pow(10, cantidad);
        int numero = ThreadLocalRandom.current().nextInt(limite);
        return String.format("%0" + cantidad + "d", numero);
    }
}
