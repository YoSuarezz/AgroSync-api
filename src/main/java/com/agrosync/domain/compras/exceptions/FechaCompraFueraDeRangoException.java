package com.agrosync.domain.compras.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class FechaCompraFueraDeRangoException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private FechaCompraFueraDeRangoException(String technicalMessage, String userMessage) {
        super(technicalMessage, userMessage);
    }

    public static FechaCompraFueraDeRangoException create() {
        var userMessage = "La fecha de compra debe estar entre los últimos 15 días y la fecha actual. No se permiten fechas futuras ni anteriores a 15 días.";
        return new FechaCompraFueraDeRangoException(userMessage, userMessage);
    }
}

