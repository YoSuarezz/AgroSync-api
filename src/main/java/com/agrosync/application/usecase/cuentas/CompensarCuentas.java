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
     * Resultado de una operación de compensación de cuentas.
     * @param montoCompensado Total que se logró compensar con cuentas existentes
     * @param saldoRestante Dinero que sobró después de compensar todas las cuentas
     */
    record ResultadoCompensacion(
            BigDecimal montoCompensado,
            BigDecimal saldoRestante
    ) {
        public static ResultadoCompensacion sinCompensacion(BigDecimal montoOriginal) {
            return new ResultadoCompensacion(BigDecimal.ZERO, montoOriginal);
        }

        public static ResultadoCompensacion vacio() {
            return new ResultadoCompensacion(BigDecimal.ZERO, BigDecimal.ZERO);
        }
    }

    /**
     * Compensa cuentas por pagar con el monto de una venta realizada.
     * Se genera un abono automático por cada cuenta compensada.
     *
     * @param usuario Usuario que tiene cuentas por pagar (como proveedor)
     * @param suscripcion Suscripción del contexto
     * @param montoDisponible Monto disponible para compensar (total de la venta)
     * @param fecha Fecha de la operación
     * @param numeroOperacion Número de la venta que origina la compensación
     * @return Resultado con monto compensado y saldo restante (para la cuenta por cobrar)
     */
    ResultadoCompensacion compensarCuentasPagarConVenta(UsuarioEntity usuario, SuscripcionEntity suscripcion,
                                              BigDecimal montoDisponible, LocalDate fecha,
                                              String numeroOperacion);

    /**
     * Compensa cuentas por cobrar con el monto de una compra realizada.
     * Se genera un cobro automático por cada cuenta compensada.
     *
     * @param usuario Usuario que tiene cuentas por cobrar (como cliente)
     * @param suscripcion Suscripción del contexto
     * @param montoDisponible Monto disponible para compensar (total de la compra)
     * @param fecha Fecha de la operación
     * @param numeroOperacion Número de la compra que origina la compensación
     * @return Resultado con monto compensado y saldo restante (para la cuenta por pagar)
     */
    ResultadoCompensacion compensarCuentasCobrarConCompra(UsuarioEntity usuario, SuscripcionEntity suscripcion,
                                                BigDecimal montoDisponible, LocalDate fecha,
                                                String numeroOperacion);
}

