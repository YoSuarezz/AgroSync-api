package com.agrosync.application.usecase.compras.impl;

import com.agrosync.domain.enums.abonos.EstadoAbonoEnum;
import com.agrosync.domain.enums.usuarios.TipoUsuarioEnum;
import com.agrosync.domain.enums.compras.EstadoCompraEnum;
import com.agrosync.domain.enums.cuentas.MetodoPagoEnum;
import com.agrosync.application.secondaryports.entity.abonos.AbonoEntity;
import com.agrosync.application.secondaryports.entity.compras.CompraEntity;
import com.agrosync.application.secondaryports.entity.animales.AnimalEntity;
import com.agrosync.application.secondaryports.entity.cuentaspagar.CuentaPagarEntity;
import com.agrosync.application.secondaryports.entity.lotes.LoteEntity;
import com.agrosync.application.secondaryports.entity.suscripcion.SuscripcionEntity;
import com.agrosync.application.secondaryports.entity.usuarios.UsuarioEntity;
import com.agrosync.application.secondaryports.mapper.compras.CompraEntityMapper;
import com.agrosync.application.secondaryports.repository.AbonoRepository;
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
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RegistrarNuevaCompraImpl implements RegistrarNuevaCompra {

    private final CompraRepository compraRepository;
    private final UsuarioRepository usuarioRepository;
    private final AbonoRepository abonoRepository;
    private final ActualizarCartera actualizarCartera;
    private final CompensarCuentas compensarCuentas;
    private final RegistrarNuevaCompraRulesValidator registrarNuevaCompraRulesValidator;
    private final RegistrarNuevoLoteRulesValidator registrarNuevoLoteRulesValidator;
    private final RegistrarNuevoAnimalRulesValidator registrarNuevoAnimalRulesValidator;
    private final CompraEntityMapper compraEntityMapper;

    public RegistrarNuevaCompraImpl(CompraRepository compraRepository,
                                    UsuarioRepository usuarioRepository,
                                    AbonoRepository abonoRepository,
                                    ActualizarCartera actualizarCartera,
                                    CompensarCuentas compensarCuentas,
                                    RegistrarNuevaCompraRulesValidator registrarNuevaCompraRulesValidator,
                                    RegistrarNuevoLoteRulesValidator registrarNuevoLoteRulesValidator,
                                    RegistrarNuevoAnimalRulesValidator registrarNuevoAnimalRulesValidator,
                                    CompraEntityMapper compraEntityMapper) {
        this.compraRepository = compraRepository;
        this.usuarioRepository = usuarioRepository;
        this.abonoRepository = abonoRepository;
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

        // Generar número de compra ANTES de la compensación (se necesita para el concepto)
        if (TextHelper.isEmpty(compra.getNumeroCompra())) {
            compra.setNumeroCompra(GenerarNumeroHelper.generarNumeroCompra(lote.getContramarca()));
        }

        // 6. Procesar compensación PRIMERO (antes de crear la cuenta por pagar)
        CompensarCuentas.ResultadoCompensacion resultadoCompensacion = procesarCompensacionYObtenerResultado(
                compra.getProveedor(), suscripcion, precioTotalCompra, fechaCompra, compra.getNumeroCompra());
        BigDecimal saldoParaCuenta = resultadoCompensacion.saldoRestante();

        // 7. Configurar cuenta por pagar con el saldo restante después de compensación
        configurarCuentaPagar(compra, suscripcion, precioTotalCompra, saldoParaCuenta, fechaCompra);

        // 8. Guardar compra
        CompraEntity compraGuardada = compraRepository.save(compra);

        // 9. Si hubo compensación y quedó saldo, crear abono inicial documentando el cruce
        if (resultadoCompensacion.huboCompensacion() && saldoParaCuenta.compareTo(BigDecimal.ZERO) > 0) {
            crearAbonoInicialPorCruceDeCuentas(compraGuardada.getCuentaPagar(), suscripcion,
                    resultadoCompensacion, fechaCompra);
        }

        // 10. Actualizar cartera si hay saldo pendiente
        actualizarCarteraSiCorresponde(compra.getProveedor(), suscripcion, saldoParaCuenta);
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
        compra.setEstado(EstadoCompraEnum.ACTIVA);
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
                                       BigDecimal montoTotal, BigDecimal saldoPendiente, LocalDate fechaCompra) {
        CuentaPagarEntity cuentaPagar = compra.getCuentaPagar();
        if (ObjectHelper.isNull(cuentaPagar)) {
            cuentaPagar = new CuentaPagarEntity();
            compra.setCuentaPagar(cuentaPagar);
        }

        cuentaPagar.setCompra(compra);
        cuentaPagar.setProveedor(compra.getProveedor());
        cuentaPagar.setSuscripcion(suscripcion);
        cuentaPagar.setMontoTotal(montoTotal);
        cuentaPagar.setSaldoPendiente(saldoPendiente);

        // Determinar estado según saldo pendiente
        if (saldoPendiente.compareTo(BigDecimal.ZERO) <= 0) {
            cuentaPagar.setEstado(EstadoCuentaEnum.PAGADA);
        } else if (saldoPendiente.compareTo(montoTotal) < 0) {
            cuentaPagar.setEstado(EstadoCuentaEnum.PARCIALMENTE_PAGADA);
        } else {
            cuentaPagar.setEstado(EstadoCuentaEnum.PENDIENTE);
        }

        cuentaPagar.setFechaEmision(fechaCompra);
        cuentaPagar.setFechaVencimiento(fechaCompra);

        if (TextHelper.isEmpty(cuentaPagar.getNumeroCuenta())) {
            cuentaPagar.setNumeroCuenta(GenerarNumeroHelper.generarNumeroCuentaPagar(compra.getNumeroCompra()));
        }
    }

    private CompensarCuentas.ResultadoCompensacion procesarCompensacionYObtenerResultado(
            UsuarioEntity proveedor, SuscripcionEntity suscripcion,
            BigDecimal precioTotalCompra, LocalDate fechaCompra,
            String numeroCompra) {
        UUID proveedorId = proveedor != null ? proveedor.getId() : null;
        if (ObjectHelper.isNull(proveedorId)) {
            return CompensarCuentas.ResultadoCompensacion.sinCompensacion(precioTotalCompra);
        }

        UsuarioEntity proveedorCompleto = usuarioRepository.findByIdAndSuscripcion_Id(proveedorId, suscripcion.getId())
                .orElse(null);

        // Si el proveedor es AMBOS, intentar compensar con sus cuentas por cobrar (como cliente)
        if (proveedorCompleto != null && proveedorCompleto.getTipoUsuario() == TipoUsuarioEnum.AMBOS) {
            return compensarCuentas.compensarCuentasCobrarConCompra(
                    proveedorCompleto, suscripcion, precioTotalCompra, fechaCompra, numeroCompra
            );
        }

        return CompensarCuentas.ResultadoCompensacion.sinCompensacion(precioTotalCompra);
    }

    /**
     * Crea un abono inicial en la cuenta por pagar que documenta el cruce de cuentas realizado.
     * Esto proporciona trazabilidad del pago parcial que ya se hizo mediante compensación.
     */
    private void crearAbonoInicialPorCruceDeCuentas(CuentaPagarEntity cuentaPagar, SuscripcionEntity suscripcion,
                                                     CompensarCuentas.ResultadoCompensacion resultado,
                                                     LocalDate fecha) {
        if (cuentaPagar == null || !resultado.huboCompensacion()) {
            return;
        }

        AbonoEntity abono = new AbonoEntity();
        abono.setCuentaPagar(cuentaPagar);
        abono.setMonto(resultado.montoCompensado());
        abono.setFechaPago(fecha.atStartOfDay());
        abono.setMetodoPago(MetodoPagoEnum.OTRO);
        abono.setConcepto(generarConceptoAbonoInicial(resultado));
        abono.setSuscripcion(suscripcion);
        abono.setEstado(EstadoAbonoEnum.ACTIVO);
        abonoRepository.save(abono);
    }

    /**
     * Genera el concepto para el abono inicial que documenta el cruce de cuentas.
     */
    private String generarConceptoAbonoInicial(CompensarCuentas.ResultadoCompensacion resultado) {
        String nombreUsuario = ObjectHelper.getDefault(resultado.nombreUsuario(), "Usuario");
        return String.format(
                "Cruce de cuentas - Se descontó %s de deuda que tenía %s",
                formatearMonto(resultado.montoCompensado()),
                nombreUsuario
        );
    }

    /**
     * Formatea un monto a formato de moneda legible
     */
    private String formatearMonto(BigDecimal monto) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));
        return formatter.format(monto);
    }

    private void actualizarCarteraSiCorresponde(UsuarioEntity proveedor, SuscripcionEntity suscripcion,
                                                 BigDecimal saldoPendiente) {
        UUID proveedorId = proveedor != null ? proveedor.getId() : null;
        if (ObjectHelper.isNull(proveedorId)) {
            return;
        }

        // Solo actualizar cartera si hay saldo pendiente por pagar
        if (saldoPendiente.compareTo(BigDecimal.ZERO) > 0) {
            actualizarCartera.incrementarCuentasPagar(proveedorId, suscripcion.getId(), saldoPendiente);
        }
    }
}

