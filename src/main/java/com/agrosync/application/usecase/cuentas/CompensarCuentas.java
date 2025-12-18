package com.agrosync.application.usecase.cuentas;

import com.agrosync.application.secondaryports.entity.suscripcion.SuscripcionEntity;
import com.agrosync.application.secondaryports.entity.usuarios.UsuarioEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Use Case para gestionar la compensación automática de cuentas.
 * Implementa el cruce de cuentas cuando un usuario es AMBOS (cliente y proveedor).
 */
public interface CompensarCuentas {

    /**
     * Compensa cuentas por pagar con el monto de una venta realizada.
     * Se genera un abono automático por cada cuenta compensada.
     *
     * @param usuario Usuario que tiene cuentas por pagar
     * @param suscripcion Suscripción del contexto
     * @param montoDisponible Monto disponible para compensar
     * @param fecha Fecha de la operación
     * @param numeroOperacion Número de la venta que origina la compensación
     * @return Monto total compensado
     */
    BigDecimal compensarCuentasPagarConVenta(UsuarioEntity usuario, SuscripcionEntity suscripcion,
                                              BigDecimal montoDisponible, LocalDate fecha,
                                              String numeroOperacion);

    /**
     * Compensa cuentas por cobrar con el monto de una compra realizada.
     * Se genera un cobro automático por cada cuenta compensada.
     *
     * @param usuario Usuario que tiene cuentas por cobrar
     * @param suscripcion Suscripción del contexto
     * @param montoDisponible Monto disponible para compensar
     * @param fecha Fecha de la operación
     * @param numeroOperacion Número de la compra que origina la compensación
     * @return Monto total compensado
     */
    BigDecimal compensarCuentasCobrarConCompra(UsuarioEntity usuario, SuscripcionEntity suscripcion,
                                                BigDecimal montoDisponible, LocalDate fecha,
                                                String numeroOperacion);
}

