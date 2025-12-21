package com.agrosync.domain.enums.compras;

/**
 * Estado de una compra en el sistema.
 * ACTIVA: La compra está vigente y sus cuentas asociadas están activas.
 * ANULADA: La compra fue revertida y todos sus efectos (animales, cuentas, cartera) fueron deshechos.
 */
public enum EstadoCompraEnum {
    ACTIVA,
    ANULADA
}

