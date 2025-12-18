package com.agrosync.application.usecase.compras.impl;

import com.agrosync.domain.enums.usuarios.TipoUsuarioEnum;
import com.agrosync.application.secondaryports.entity.compras.CompraEntity;
import com.agrosync.application.secondaryports.entity.animales.AnimalEntity;
import com.agrosync.application.secondaryports.entity.cuentaspagar.CuentaPagarEntity;
import com.agrosync.application.secondaryports.entity.lotes.LoteEntity;
import com.agrosync.application.secondaryports.entity.suscripcion.SuscripcionEntity;
import com.agrosync.application.secondaryports.entity.usuarios.UsuarioEntity;
import com.agrosync.application.secondaryports.mapper.compras.CompraEntityMapper;
import com.agrosync.application.secondaryports.repository.CompraRepository;
import com.agrosync.application.secondaryports.repository.UsuarioRepository;
import com.agrosync.application.usecase.animales.rulesvalidator.RegistrarNuevoAnimalRulesValidator;
import com.agrosync.application.usecase.carteras.ActualizarCartera;
import com.agrosync.application.usecase.compras.RegistrarNuevaCompra;
import com.agrosync.application.usecase.cuentas.CompensarCuentas;
import com.agrosync.domain.enums.animales.EstadoAnimalEnum;
import com.agrosync.domain.enums.cuentas.EstadoCuentaEnum;
import com.agrosync.application.usecase.compras.rulesvalidator.RegistrarNuevaCompraRulesValidator;
import com.agrosync.application.usecase.lotes.rulesvalidator.RegistrarNuevoLoteRulesValidator;
import com.agrosync.crosscutting.helpers.GenerarNumeroHelper;
import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.crosscutting.helpers.TextHelper;
import com.agrosync.domain.compras.CompraDomain;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RegistrarNuevaCompraImpl implements RegistrarNuevaCompra {

    private final CompraRepository compraRepository;
    private final UsuarioRepository usuarioRepository;
    private final ActualizarCartera actualizarCartera;
    private final CompensarCuentas compensarCuentas;
    private final RegistrarNuevaCompraRulesValidator registrarNuevaCompraRulesValidator;
    private final RegistrarNuevoLoteRulesValidator registrarNuevoLoteRulesValidator;
    private final RegistrarNuevoAnimalRulesValidator registrarNuevoAnimalRulesValidator;
    private final CompraEntityMapper compraEntityMapper;

    public RegistrarNuevaCompraImpl(CompraRepository compraRepository,
                                    UsuarioRepository usuarioRepository,
                                    ActualizarCartera actualizarCartera,
                                    CompensarCuentas compensarCuentas,
                                    RegistrarNuevaCompraRulesValidator registrarNuevaCompraRulesValidator,
                                    RegistrarNuevoLoteRulesValidator registrarNuevoLoteRulesValidator,
                                    RegistrarNuevoAnimalRulesValidator registrarNuevoAnimalRulesValidator,
                                    CompraEntityMapper compraEntityMapper) {
        this.compraRepository = compraRepository;
        this.usuarioRepository = usuarioRepository;
        this.actualizarCartera = actualizarCartera;
        this.compensarCuentas = compensarCuentas;
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
        validarAnimales(data);

        // 5. Mapear a entidad y configurar relaciones
        CompraEntity compra = compraEntityMapper.toEntity(data);
        SuscripcionEntity suscripcion = SuscripcionEntity.create(data.getSuscripcionId());

        configurarCompra(compra, suscripcion, fechaCompra);

        LoteEntity lote = compra.getLote();
        configurarLote(lote, compra, suscripcion, fechaCompra);

        BigDecimal[] totales = procesarAnimales(lote, suscripcion);
        BigDecimal pesoTotal = totales[0];
        BigDecimal precioTotalCompra = totales[1];

        lote.setPesoTotal(pesoTotal);
        compra.setPrecioTotalCompra(precioTotalCompra);

        configurarCuentaPagar(compra, suscripcion, precioTotalCompra, fechaCompra);

        // 6. Guardar compra
        compraRepository.save(compra);

        // 7. Procesar compensación y actualizar cartera
        procesarCompensacionYCartera(compra, suscripcion, precioTotalCompra, fechaCompra);
    }

    private void validarAnimales(CompraDomain data) {
        if (!ObjectHelper.isNull(data.getLote().getAnimales())) {
            for (var animalDomain : data.getLote().getAnimales()) {
                if (!ObjectHelper.isNull(animalDomain)) {
                    animalDomain.setSuscripcionId(data.getSuscripcionId());
                    registrarNuevoAnimalRulesValidator.validar(animalDomain);
                }
            }
        }
    }

    private void configurarCompra(CompraEntity compra, SuscripcionEntity suscripcion, LocalDate fechaCompra) {
        compra.setSuscripcion(suscripcion);
        compra.setFechaCompra(fechaCompra);
    }

    private void configurarLote(LoteEntity lote, CompraEntity compra, SuscripcionEntity suscripcion, LocalDate fechaCompra) {
        lote.setCompra(compra);
        lote.setSuscripcion(suscripcion);
        lote.setFecha(fechaCompra);

        if (TextHelper.isEmpty(lote.getNumeroLote())) {
            lote.setNumeroLote(GenerarNumeroHelper.generarNumeroLote(lote.getContramarca(), lote.getFecha()));
        }
    }

    private BigDecimal[] procesarAnimales(LoteEntity lote, SuscripcionEntity suscripcion) {
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
                    animal.setNumeroAnimal(GenerarNumeroHelper.generarNumeroAnimal(
                            lote.getContramarca(), animal.getSlot(), lote.getFecha()));
                }

                BigDecimal peso = ObjectHelper.getDefault(animal.getPeso(), BigDecimal.ZERO);
                BigDecimal precioKiloCompra = ObjectHelper.getDefault(animal.getPrecioKiloCompra(), BigDecimal.ZERO);

                pesoTotal = pesoTotal.add(peso);
                precioTotalCompra = precioTotalCompra.add(peso.multiply(precioKiloCompra));
            }
        }

        return new BigDecimal[]{pesoTotal, precioTotalCompra};
    }

    private void configurarCuentaPagar(CompraEntity compra, SuscripcionEntity suscripcion,
                                       BigDecimal precioTotalCompra, LocalDate fechaCompra) {
        if (TextHelper.isEmpty(compra.getNumeroCompra())) {
            compra.setNumeroCompra(GenerarNumeroHelper.generarNumeroCompra(compra.getLote().getContramarca()));
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
            cuentaPagar.setNumeroCuenta(GenerarNumeroHelper.generarNumeroCuentaPagar(compra.getNumeroCompra()));
        }
    }

    private void procesarCompensacionYCartera(CompraEntity compra, SuscripcionEntity suscripcion,
                                               BigDecimal precioTotalCompra, LocalDate fechaCompra) {
        UUID proveedorId = compra.getProveedor() != null ? compra.getProveedor().getId() : null;
        if (ObjectHelper.isNull(proveedorId)) {
            return;
        }

        UsuarioEntity proveedor = usuarioRepository.findByIdAndSuscripcion_Id(proveedorId, suscripcion.getId())
                .orElse(null);

        BigDecimal montoCompensado = BigDecimal.ZERO;
        if (proveedor != null && proveedor.getTipoUsuario() == TipoUsuarioEnum.AMBOS) {
            montoCompensado = compensarCuentas.compensarCuentasCobrarConCompra(
                    proveedor, suscripcion, precioTotalCompra, fechaCompra, compra.getNumeroCompra()
            );
        }

        BigDecimal montoNeto = precioTotalCompra.subtract(montoCompensado);
        if (montoNeto.compareTo(BigDecimal.ZERO) > 0) {
            actualizarCartera.incrementarCuentasCobrar(proveedorId, suscripcion.getId(), montoNeto);
        }
    }
}

