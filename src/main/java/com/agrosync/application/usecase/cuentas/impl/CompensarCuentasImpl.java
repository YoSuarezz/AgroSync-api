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
import com.agrosync.domain.enums.cuentas.EstadoCuentaEnum;
import com.agrosync.domain.enums.cuentas.MetodoPagoEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

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

            // Crear abono automático
            AbonoEntity abono = crearAbonoAutomatico(
                    cuenta, suscripcion, montoAAbonar, fecha,
                    generarConceptoAbono(nombreUsuario, cuenta.getNumeroCuenta(), montoAAbonar, numeroOperacion)
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

        return new ResultadoCompensacion(totalCompensado, saldoDisponible);
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

            // Crear cobro automático
            CobroEntity cobro = crearCobroAutomatico(
                    cuenta, suscripcion, montoACobrar, fecha,
                    generarConceptoCobro(nombreUsuario, cuenta.getNumeroCuenta(), montoACobrar, numeroOperacion)
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

        return new ResultadoCompensacion(totalCompensado, saldoDisponible);
    }

    private AbonoEntity crearAbonoAutomatico(CuentaPagarEntity cuenta, SuscripcionEntity suscripcion,
                                              BigDecimal monto, LocalDate fecha, String concepto) {
        AbonoEntity abono = new AbonoEntity();
        abono.setCuentaPagar(cuenta);
        abono.setMonto(monto);
        abono.setFechaPago(fecha.atStartOfDay());
        abono.setMetodoPago(MetodoPagoEnum.OTRO);
        abono.setConcepto(concepto);
        abono.setSuscripcion(suscripcion);
        return abono;
    }

    private CobroEntity crearCobroAutomatico(CuentaCobrarEntity cuenta, SuscripcionEntity suscripcion,
                                              BigDecimal monto, LocalDate fecha, String concepto) {
        CobroEntity cobro = new CobroEntity();
        cobro.setCuentaCobrar(cuenta);
        cobro.setMonto(monto);
        cobro.setFechaCobro(fecha.atStartOfDay());
        cobro.setMetodoPago(MetodoPagoEnum.OTRO);
        cobro.setConcepto(concepto);
        cobro.setSuscripcion(suscripcion);
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
     */
    private String generarConceptoAbono(String nombreUsuario, String numeroCuentaPagar,
                                         BigDecimal monto, String numeroVenta) {
        return String.format(
                "Cruce de cuentas: Pago de $%s a %s por venta %s (Cuenta %s)",
                monto.toPlainString(),
                nombreUsuario,
                numeroVenta,
                ObjectHelper.getDefault(numeroCuentaPagar, "N/A")
        );
    }

    /**
     * Genera concepto simplificado para cobro automático por cruce de cuentas.
     */
    private String generarConceptoCobro(String nombreUsuario, String numeroCuentaCobrar,
                                         BigDecimal monto, String numeroCompra) {
        return String.format(
                "Cruce de cuentas: Cobro de $%s a %s por compra %s (Cuenta %s)",
                monto.toPlainString(),
                nombreUsuario,
                numeroCompra,
                ObjectHelper.getDefault(numeroCuentaCobrar, "N/A")
        );
    }
}
