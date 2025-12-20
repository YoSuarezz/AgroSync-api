package com.agrosync.domain.enums.abonos;

/**
 * Estado de un abono en el sistema.
 * ACTIVO: El abono está vigente y afecta la cuenta por pagar.
 * ANULADO: El abono fue revertido y sus efectos fueron deshechos.
 */
public enum EstadoAbonoEnum {
    ACTIVO,
    ANULADO
}

