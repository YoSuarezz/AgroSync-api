package com.agrosync.application.usecase.carteras;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Use Case para actualizar saldos de carteras.
 * Centraliza la lógica de actualización de saldos de carteras.
 */
public interface ActualizarCartera {

    /**
     * Incrementa el saldo de cuentas por cobrar de un usuario (proveedor).
     * Se usa cuando se registra una compra (el proveedor nos debe entregar productos).
     */
    void incrementarCuentasCobrar(UUID usuarioId, UUID suscripcionId, BigDecimal monto);

    /**
     * Incrementa el saldo de cuentas por pagar de un usuario (cliente).
     * Se usa cuando se registra una venta (el cliente nos debe pagar).
     */
    void incrementarCuentasPagar(UUID usuarioId, UUID suscripcionId, BigDecimal monto);

    /**
     * Reduce el saldo de cuentas por cobrar cuando se recibe un cobro.
     * El saldo actual aumenta porque nos pagaron.
     */
    void reducirCuentasCobrarPorCobro(UUID usuarioId, UUID suscripcionId, BigDecimal montoCobro);

    /**
     * Reduce el saldo de cuentas por pagar cuando se hace un abono.
     * El saldo actual disminuye porque pagamos una deuda.
     */
    void reducirCuentasPagarPorAbono(UUID usuarioId, UUID suscripcionId, BigDecimal montoAbono);
}

