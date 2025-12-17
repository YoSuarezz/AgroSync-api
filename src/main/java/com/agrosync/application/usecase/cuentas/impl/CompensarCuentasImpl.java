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
import java.util.List;

@Service
@Transactional
public class CompensarCuentasImpl implements CompensarCuentas {

    private final CuentaPagarRepository cuentaPagarRepository;
    private final CuentaCobrarRepository cuentaCobrarRepository;
    private final AbonoRepository abonoRepository;
    private final CobroRepository cobroRepository;
    private final ActualizarCartera actualizarCartera;

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
    public BigDecimal compensarCuentasPagarConVenta(UsuarioEntity usuario, SuscripcionEntity suscripcion,
                                                     BigDecimal montoDisponible, LocalDate fecha,
                                                     String numeroOperacion) {
        if (ObjectHelper.isNull(usuario) || ObjectHelper.isNull(usuario.getId())) {
            return BigDecimal.ZERO;
        }

        List<CuentaPagarEntity> cuentasPendientes = cuentaPagarRepository
                .findByProveedor_IdAndSuscripcion_IdAndEstadoNot(
                        usuario.getId(),
                        suscripcion.getId(),
                        EstadoCuentaEnum.PAGADA
                );

        if (ObjectHelper.isNull(cuentasPendientes) || cuentasPendientes.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal saldoDisponible = ObjectHelper.getDefault(montoDisponible, BigDecimal.ZERO);
        BigDecimal totalCompensado = BigDecimal.ZERO;
        String nombreUsuario = ObjectHelper.getDefault(usuario.getNombre(), "Usuario");

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

            AbonoEntity abono = crearAbonoAutomatico(
                    cuenta, suscripcion, montoAAbonar, fecha,
                    generarConceptoAbono(montoAAbonar, cuenta.getNumeroCuenta(), nombreUsuario,
                            numeroOperacion, saldoPendiente, nuevoSaldo)
            );
            abonoRepository.save(abono);

            actualizarCuentaPagar(cuenta, nuevoSaldo);
            cuentaPagarRepository.save(cuenta);

            actualizarCartera.reducirCuentasPagarPorAbono(usuario.getId(), suscripcion.getId(), montoAAbonar);

            saldoDisponible = saldoDisponible.subtract(montoAAbonar);
            totalCompensado = totalCompensado.add(montoAAbonar);
        }

        return totalCompensado;
    }

    @Override
    public BigDecimal compensarCuentasCobrarConCompra(UsuarioEntity usuario, SuscripcionEntity suscripcion,
                                                       BigDecimal montoDisponible, LocalDate fecha,
                                                       String numeroOperacion) {
        if (ObjectHelper.isNull(usuario) || ObjectHelper.isNull(usuario.getId())) {
            return BigDecimal.ZERO;
        }

        List<CuentaCobrarEntity> cuentasPendientes = cuentaCobrarRepository
                .findByCliente_IdAndSuscripcion_IdAndEstadoNot(
                        usuario.getId(),
                        suscripcion.getId(),
                        EstadoCuentaEnum.COBRADA
                );

        if (ObjectHelper.isNull(cuentasPendientes) || cuentasPendientes.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal saldoDisponible = ObjectHelper.getDefault(montoDisponible, BigDecimal.ZERO);
        BigDecimal totalCompensado = BigDecimal.ZERO;
        String nombreUsuario = ObjectHelper.getDefault(usuario.getNombre(), "Usuario");

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

            CobroEntity cobro = crearCobroAutomatico(
                    cuenta, suscripcion, montoACobrar, fecha,
                    generarConceptoCobro(montoACobrar, cuenta.getNumeroCuenta(), nombreUsuario,
                            numeroOperacion, saldoPendiente, nuevoSaldo)
            );
            cobroRepository.save(cobro);

            actualizarCuentaCobrar(cuenta, nuevoSaldo);
            cuentaCobrarRepository.save(cuenta);

            actualizarCartera.reducirCuentasCobrarPorCobro(usuario.getId(), suscripcion.getId(), montoACobrar);

            saldoDisponible = saldoDisponible.subtract(montoACobrar);
            totalCompensado = totalCompensado.add(montoACobrar);
        }

        return totalCompensado;
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

    private String generarConceptoAbono(BigDecimal monto, String numeroCuenta, String nombreUsuario,
                                         String numeroOperacion, BigDecimal saldoAnterior, BigDecimal saldoNuevo) {
        return String.format(
                "Cruce de cuentas automático: Abono de $%s a cuenta por pagar %s del usuario %s, " +
                "originado por venta %s. Saldo anterior: $%s, Saldo después del abono: $%s",
                monto.toPlainString(),
                ObjectHelper.getDefault(numeroCuenta, "N/A"),
                nombreUsuario,
                numeroOperacion,
                saldoAnterior.toPlainString(),
                saldoNuevo.toPlainString()
        );
    }

    private String generarConceptoCobro(BigDecimal monto, String numeroCuenta, String nombreUsuario,
                                         String numeroOperacion, BigDecimal saldoAnterior, BigDecimal saldoNuevo) {
        return String.format(
                "Cruce de cuentas automático: Cobro de $%s a cuenta por cobrar %s del usuario %s, " +
                "originado por compra %s. Saldo anterior: $%s, Saldo después del cobro: $%s",
                monto.toPlainString(),
                ObjectHelper.getDefault(numeroCuenta, "N/A"),
                nombreUsuario,
                numeroOperacion,
                saldoAnterior.toPlainString(),
                saldoNuevo.toPlainString()
        );
    }
}

