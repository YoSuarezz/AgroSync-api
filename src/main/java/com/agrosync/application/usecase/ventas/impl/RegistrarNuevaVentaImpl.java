package com.agrosync.application.usecase.ventas.impl;

import com.agrosync.domain.enums.animales.EstadoAnimalEnum;
import com.agrosync.domain.enums.cuentas.EstadoCuentaEnum;
import com.agrosync.domain.enums.usuarios.TipoUsuarioEnum;
import com.agrosync.application.secondaryports.entity.animales.AnimalEntity;
import com.agrosync.application.secondaryports.entity.cuentascobrar.CuentaCobrarEntity;
import com.agrosync.application.secondaryports.entity.suscripcion.SuscripcionEntity;
import com.agrosync.application.secondaryports.entity.usuarios.UsuarioEntity;
import com.agrosync.application.secondaryports.entity.ventas.VentaEntity;
import com.agrosync.application.secondaryports.mapper.ventas.VentaEntityMapper;
import com.agrosync.application.secondaryports.repository.AnimalRepository;
import com.agrosync.application.secondaryports.repository.UsuarioRepository;
import com.agrosync.application.secondaryports.repository.VentaRepository;
import com.agrosync.application.usecase.carteras.ActualizarCartera;
import com.agrosync.application.usecase.cuentas.CompensarCuentas;
import com.agrosync.application.usecase.ventas.RegistrarNuevaVenta;
import com.agrosync.application.usecase.ventas.rulesvalidator.RegistrarNuevaVentaRulesValidator;
import com.agrosync.crosscutting.helpers.GenerarNumeroHelper;
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
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RegistrarNuevaVentaImpl implements RegistrarNuevaVenta {

    private final VentaRepository ventaRepository;
    private final AnimalRepository animalRepository;
    private final UsuarioRepository usuarioRepository;
    private final ActualizarCartera actualizarCartera;
    private final CompensarCuentas compensarCuentas;
    private final RegistrarNuevaVentaRulesValidator registrarNuevaVentaRulesValidator;
    private final VentaEntityMapper ventaEntityMapper;

    public RegistrarNuevaVentaImpl(VentaRepository ventaRepository,
                                   AnimalRepository animalRepository,
                                   UsuarioRepository usuarioRepository,
                                   ActualizarCartera actualizarCartera,
                                   CompensarCuentas compensarCuentas,
                                   RegistrarNuevaVentaRulesValidator registrarNuevaVentaRulesValidator,
                                   VentaEntityMapper ventaEntityMapper) {
        this.ventaRepository = ventaRepository;
        this.animalRepository = animalRepository;
        this.usuarioRepository = usuarioRepository;
        this.actualizarCartera = actualizarCartera;
        this.compensarCuentas = compensarCuentas;
        this.registrarNuevaVentaRulesValidator = registrarNuevaVentaRulesValidator;
        this.ventaEntityMapper = ventaEntityMapper;
    }

    @Override
    public void ejecutar(VentaDomain data) {
        // 1. Validar reglas de la venta
        registrarNuevaVentaRulesValidator.validar(data);

        // 2. Mapear y configurar venta
        VentaEntity venta = ventaEntityMapper.toEntity(data);
        SuscripcionEntity suscripcion = SuscripcionEntity.create(data.getSuscripcionId());
        venta.setSuscripcion(suscripcion);

        LocalDate fechaVenta = ObjectHelper.getDefault(data.getFechaVenta(), LocalDate.now());
        venta.setFechaVenta(fechaVenta);

        if (TextHelper.isEmpty(venta.getNumeroVenta())) {
            venta.setNumeroVenta(GenerarNumeroHelper.generarNumeroVenta());
        }

        // 3. Obtener y procesar animales
        List<AnimalEntity> animalesVendidos = obtenerAnimalesParaVenta(data.getAnimales(), suscripcion);
        venta.setAnimales(animalesVendidos);

        // 4. Calcular precio total
        BigDecimal precioTotalVenta = calcularPrecioTotal(animalesVendidos);
        venta.setPrecioTotalVenta(precioTotalVenta);

        // 5. Configurar cuenta por cobrar
        configurarCuentaCobrar(venta, suscripcion, precioTotalVenta, fechaVenta, data);

        // 6. Guardar venta
        VentaEntity ventaGuardada = ventaRepository.save(venta);

        // 7. Asociar animales a la venta
        asociarAnimalesAVenta(ventaGuardada, animalesVendidos, suscripcion);

        // 8. Procesar compensación y actualizar cartera
        procesarCompensacionYCartera(venta.getCliente(), suscripcion, precioTotalVenta, fechaVenta, venta.getNumeroVenta());
    }

    private void configurarCuentaCobrar(VentaEntity venta, SuscripcionEntity suscripcion,
                                        BigDecimal precioTotalVenta, LocalDate fechaVenta, VentaDomain data) {
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
            cuentaCobrar.setNumeroCuenta(GenerarNumeroHelper.generarNumeroCuentaCobrar(venta.getNumeroVenta()));
        }
    }

    private void procesarCompensacionYCartera(UsuarioEntity cliente, SuscripcionEntity suscripcion,
                                               BigDecimal precioTotalVenta, LocalDate fechaVenta, String numeroVenta) {
        UUID clienteId = cliente != null ? cliente.getId() : null;
        if (ObjectHelper.isNull(clienteId)) {
            return;
        }

        UsuarioEntity clienteCompleto = usuarioRepository.findByIdAndSuscripcion_Id(clienteId, suscripcion.getId())
                .orElse(null);

        BigDecimal montoCompensado = BigDecimal.ZERO;
        if (clienteCompleto != null && clienteCompleto.getTipoUsuario() == TipoUsuarioEnum.AMBOS) {
            montoCompensado = compensarCuentas.compensarCuentasPagarConVenta(
                    clienteCompleto, suscripcion, precioTotalVenta, fechaVenta, numeroVenta
            );
        }

        BigDecimal montoNeto = precioTotalVenta.subtract(montoCompensado);
        if (montoNeto.compareTo(BigDecimal.ZERO) > 0) {
            actualizarCartera.incrementarCuentasPagar(clienteId, suscripcion.getId(), montoNeto);
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
}
