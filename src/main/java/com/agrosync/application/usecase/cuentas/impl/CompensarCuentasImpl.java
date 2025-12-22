package com.agrosync.application.usecase.cuentas.impl;

import com.agrosync.application.secondaryports.entity.abonos.AbonoEntity;
import com.agrosync.application.secondaryports.entity.cobros.CobroEntity;
import com.agrosync.application.secondaryports.entity.cuentascobrar.CuentaCobrarEntity;
import com.agrosync.application.secondaryports.entity.cuentaspagar.CuentaPagarEntity;
import com.agrosync.application.secondaryports.entity.suscripcion.SuscripcionEntity;
import com.agrosync.application.secondaryports.entity.usuarios.UsuarioEntity;
import com.agrosync.application.secondaryports.repository.AbonoRepository;
import com.agrosync.application.secondaryports.repository.CobroRepository;
import com.agrosync.application.secondaryports.repository.CuentaCobrarRepository;
import com.agrosync.application.secondaryports.repository.CuentaPagarRepository;
import com.agrosync.application.usecase.carteras.ActualizarCartera;
import com.agrosync.application.usecase.cuentas.CompensarCuentas;
import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.domain.enums.abonos.EstadoAbonoEnum;
import com.agrosync.domain.enums.cobros.EstadoCobroEnum;
import com.agrosync.domain.enums.cuentas.EstadoCuentaEnum;
import com.agrosync.domain.enums.cuentas.MetodoPagoEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Service
@Transactional
public class CompensarCuentasImpl implements CompensarCuentas {

    private final CuentaPagarRepository cuentaPagarRepository;
    private final CuentaCobrarRepository cuentaCobrarRepository;
    private final AbonoRepository abonoRepository;
    private final CobroRepository cobroRepository;
    private final ActualizarCartera actualizarCartera;

    private static final List<EstadoCuentaEnum> ESTADOS_PENDIENTES_PAGAR = Arrays.asList(
            EstadoCuentaEnum.PENDIENTE,
            EstadoCuentaEnum.PARCIALMENTE_PAGADA
    );

    private static final List<EstadoCuentaEnum> ESTADOS_PENDIENTES_COBRAR = Arrays.asList(
            EstadoCuentaEnum.PENDIENTE,
            EstadoCuentaEnum.PARCIALMENTE_COBRADA
    );

    public CompensarCuentasImpl(CuentaPagarRepository cuentaPagarRepository,
                                 CuentaCobrarRepository cuentaCobrarRepository,
                                 AbonoRepository abonoRepository,
                                 CobroRepository cobroRepository,
                                 ActualizarCartera actualizarCartera) {
        this.cuentaPagarRepository = cuentaPagarRepository;
        this.cuentaCobrarRepository = cuentaCobrarRepository;
        this.abonoRepository = abonoRepository;
        this.cobroRepository = cobroRepository;
        this.actualizarCartera = actualizarCartera;
    }

    @Override
    public ResultadoCompensacion compensarCuentasPagarConVenta(UsuarioEntity usuario, SuscripcionEntity suscripcion,
                                                     BigDecimal montoDisponible, LocalDate fecha,
                                                     String numeroOperacion) {
        if (ObjectHelper.isNull(usuario) || ObjectHelper.isNull(usuario.getId())) {
            return ResultadoCompensacion.sinCompensacion(montoDisponible);
        }

        // Buscar cuentas por pagar pendientes donde el usuario es PROVEEDOR (le debemos)
        List<CuentaPagarEntity> cuentasPendientes = cuentaPagarRepository
                .findByProveedor_IdAndSuscripcion_IdAndEstadoInOrderByFechaEmisionAsc(
                        usuario.getId(),
                        suscripcion.getId(),
                        ESTADOS_PENDIENTES_PAGAR
                );

        if (ObjectHelper.isNull(cuentasPendientes) || cuentasPendientes.isEmpty()) {
            // No hay cuentas por pagar pendientes, todo el monto va a la cuenta por cobrar
            return ResultadoCompensacion.sinCompensacion(montoDisponible);
        }

        BigDecimal saldoDisponible = ObjectHelper.getDefault(montoDisponible, BigDecimal.ZERO);
        BigDecimal totalCompensado = BigDecimal.ZERO;
        String nombreUsuario = ObjectHelper.getDefault(usuario.getNombre(), "Usuario");
        BigDecimal montoOriginal = saldoDisponible;

        // Compensar cada cuenta pendiente (FIFO - más antigua primero)
        for (CuentaPagarEntity cuenta : cuentasPendientes) {
            if (saldoDisponible.compareTo(BigDecimal.ZERO) <= 0) {
                break;
            }

            BigDecimal saldoPendiente = ObjectHelper.getDefault(cuenta.getSaldoPendiente(), BigDecimal.ZERO);
            if (saldoPendiente.compareTo(BigDecimal.ZERO) <= 0) {
                continue;
            }

            BigDecimal montoAAbonar = saldoDisponible.min(saldoPendiente);
            BigDecimal nuevoSaldo = saldoPendiente.subtract(montoAAbonar);

            // Crear abono automático con mensaje simplificado
            AbonoEntity abono = crearAbonoAutomatico(
                    cuenta, suscripcion, montoAAbonar, fecha,
                    generarConceptoAbono(nombreUsuario, montoAAbonar, numeroOperacion)
            );
            abonoRepository.save(abono);

            // Actualizar cuenta por pagar
            actualizarCuentaPagar(cuenta, nuevoSaldo);
            cuentaPagarRepository.save(cuenta);

            // Actualizar cartera del proveedor
            actualizarCartera.reducirCuentasPagarPorAbono(usuario.getId(), suscripcion.getId(), montoAAbonar);

            saldoDisponible = saldoDisponible.subtract(montoAAbonar);
            totalCompensado = totalCompensado.add(montoAAbonar);
        }

        return new ResultadoCompensacion(totalCompensado, saldoDisponible, nombreUsuario, montoOriginal);
    }

    @Override
    public ResultadoCompensacion compensarCuentasCobrarConCompra(UsuarioEntity usuario, SuscripcionEntity suscripcion,
                                                       BigDecimal montoDisponible, LocalDate fecha,
                                                       String numeroOperacion) {
        if (ObjectHelper.isNull(usuario) || ObjectHelper.isNull(usuario.getId())) {
            return ResultadoCompensacion.sinCompensacion(montoDisponible);
        }

        // Buscar cuentas por cobrar pendientes donde el usuario es CLIENTE (nos debe)
        List<CuentaCobrarEntity> cuentasPendientes = cuentaCobrarRepository
                .findByCliente_IdAndSuscripcion_IdAndEstadoInOrderByFechaEmisionAsc(
                        usuario.getId(),
                        suscripcion.getId(),
                        ESTADOS_PENDIENTES_COBRAR
                );

        if (ObjectHelper.isNull(cuentasPendientes) || cuentasPendientes.isEmpty()) {
            // No hay cuentas por cobrar pendientes, todo el monto va a la cuenta por pagar
            return ResultadoCompensacion.sinCompensacion(montoDisponible);
        }

        BigDecimal saldoDisponible = ObjectHelper.getDefault(montoDisponible, BigDecimal.ZERO);
        BigDecimal totalCompensado = BigDecimal.ZERO;
        String nombreUsuario = ObjectHelper.getDefault(usuario.getNombre(), "Usuario");
        BigDecimal montoOriginal = saldoDisponible;

        // Compensar cada cuenta pendiente (FIFO - más antigua primero)
        for (CuentaCobrarEntity cuenta : cuentasPendientes) {
            if (saldoDisponible.compareTo(BigDecimal.ZERO) <= 0) {
                break;
            }

            BigDecimal saldoPendiente = ObjectHelper.getDefault(cuenta.getSaldoPendiente(), BigDecimal.ZERO);
            if (saldoPendiente.compareTo(BigDecimal.ZERO) <= 0) {
                continue;
            }

            BigDecimal montoACobrar = saldoDisponible.min(saldoPendiente);
            BigDecimal nuevoSaldo = saldoPendiente.subtract(montoACobrar);

            // Crear cobro automático con mensaje simplificado
            CobroEntity cobro = crearCobroAutomatico(
                    cuenta, suscripcion, montoACobrar, fecha,
                    generarConceptoCobro(nombreUsuario, montoACobrar, numeroOperacion)
            );
            cobroRepository.save(cobro);

            // Actualizar cuenta por cobrar
            actualizarCuentaCobrar(cuenta, nuevoSaldo);
            cuentaCobrarRepository.save(cuenta);

            // Actualizar cartera del cliente
            actualizarCartera.reducirCuentasCobrarPorCobro(usuario.getId(), suscripcion.getId(), montoACobrar);

            saldoDisponible = saldoDisponible.subtract(montoACobrar);
            totalCompensado = totalCompensado.add(montoACobrar);
        }

        return new ResultadoCompensacion(totalCompensado, saldoDisponible, nombreUsuario, montoOriginal);
    }

    private AbonoEntity crearAbonoAutomatico(CuentaPagarEntity cuenta, SuscripcionEntity suscripcion,
                                              BigDecimal monto, LocalDate fecha, String concepto) {
        AbonoEntity abono = new AbonoEntity();
        abono.setCuentaPagar(cuenta);
        abono.setMonto(monto);
        abono.setFechaPago(fecha.atStartOfDay());
        abono.setMetodoPago(MetodoPagoEnum.CRUCE_DE_CUENTAS);
        abono.setConcepto(concepto);
        abono.setSuscripcion(suscripcion);
        abono.setEstado(EstadoAbonoEnum.ACTIVO);
        return abono;
    }

    private CobroEntity crearCobroAutomatico(CuentaCobrarEntity cuenta, SuscripcionEntity suscripcion,
                                              BigDecimal monto, LocalDate fecha, String concepto) {
        CobroEntity cobro = new CobroEntity();
        cobro.setCuentaCobrar(cuenta);
        cobro.setMonto(monto);
        cobro.setFechaCobro(fecha.atStartOfDay());
        cobro.setMetodoPago(MetodoPagoEnum.CRUCE_DE_CUENTAS);
        cobro.setConcepto(concepto);
        cobro.setSuscripcion(suscripcion);
        cobro.setEstado(EstadoCobroEnum.ACTIVO);
        return cobro;
    }

    private void actualizarCuentaPagar(CuentaPagarEntity cuenta, BigDecimal nuevoSaldo) {
        cuenta.setSaldoPendiente(nuevoSaldo.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : nuevoSaldo);
        if (nuevoSaldo.compareTo(BigDecimal.ZERO) <= 0) {
            cuenta.setEstado(EstadoCuentaEnum.PAGADA);
        } else {
            cuenta.setEstado(EstadoCuentaEnum.PARCIALMENTE_PAGADA);
        }
    }

    private void actualizarCuentaCobrar(CuentaCobrarEntity cuenta, BigDecimal nuevoSaldo) {
        cuenta.setSaldoPendiente(nuevoSaldo.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : nuevoSaldo);
        if (nuevoSaldo.compareTo(BigDecimal.ZERO) <= 0) {
            cuenta.setEstado(EstadoCuentaEnum.COBRADA);
        } else {
            cuenta.setEstado(EstadoCuentaEnum.PARCIALMENTE_COBRADA);
        }
    }

    /**
     * Genera concepto simplificado para abono automático por cruce de cuentas.
     * Mensaje claro para la secretaria sin números de cuenta confusos.
     */
    private String generarConceptoAbono(String nombreUsuario, BigDecimal monto, String numeroOperacion) {
        return String.format(
                "Cruce de cuentas - Pago automático de %s a %s por venta realizada. Operación: %s",
                formatearMonto(monto),
                nombreUsuario,
                numeroOperacion
        );
    }

    /**
     * Genera concepto simplificado para cobro automático por cruce de cuentas.
     * Mensaje claro para la secretaria sin números de cuenta confusos.
     */
    private String generarConceptoCobro(String nombreUsuario, BigDecimal monto, String numeroOperacion) {
        return String.format(
                "Cruce de cuentas - Cobro automático de %s a %s por compra realizada. Operación: %s",
                formatearMonto(monto),
                nombreUsuario,
                numeroOperacion
        );
    }

    /**
     * Formatea un monto a formato de moneda legible (ej: $1.500.000)
     */
    private String formatearMonto(BigDecimal monto) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));
        return formatter.format(monto);
    }
}
