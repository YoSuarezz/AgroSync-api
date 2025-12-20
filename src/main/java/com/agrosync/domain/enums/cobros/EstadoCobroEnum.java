package com.agrosync.domain.enums.cobros;

/**
 * Estado de un cobro en el sistema.
 * ACTIVO: El cobro está vigente y afecta la cuenta por cobrar.
 * ANULADO: El cobro fue revertido y sus efectos fueron deshechos.
 */
public enum EstadoCobroEnum {
    ACTIVO,
    ANULADO
}

