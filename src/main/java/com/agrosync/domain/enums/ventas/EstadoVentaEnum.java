package com.agrosync.domain.enums.ventas;

/**
 * Estado de una venta en el sistema.
 * ACTIVA: La venta está vigente y sus cuentas asociadas están activas.
 * ANULADA: La venta fue revertida y todos sus efectos (animales, cuentas, cartera) fueron deshechos.
 */
public enum EstadoVentaEnum {
    ACTIVA,
    ANULADA
}

