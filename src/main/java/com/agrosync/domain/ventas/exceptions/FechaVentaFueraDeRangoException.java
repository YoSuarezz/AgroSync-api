package com.agrosync.domain.ventas.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class FechaVentaFueraDeRangoException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private FechaVentaFueraDeRangoException(String technicalMessage, String userMessage) {
        super(technicalMessage, userMessage);
    }

    public static FechaVentaFueraDeRangoException create() {
        var userMessage = "La fecha de venta debe estar entre los últimos 15 días y la fecha actual. No se permiten fechas futuras ni anteriores a 15 días.";
        return new FechaVentaFueraDeRangoException(userMessage, userMessage);
    }
}

