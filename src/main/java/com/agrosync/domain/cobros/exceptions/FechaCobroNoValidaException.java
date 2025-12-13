package com.agrosync.domain.cobros.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class FechaCobroNoValidaException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private FechaCobroNoValidaException(final String userMessage) {
        super(userMessage, userMessage, new Exception());
    }

    public static FechaCobroNoValidaException create() {
        var userMessage = "La fecha del cobro debe estar entre los últimos 15 días y la fecha actual.";
        return new FechaCobroNoValidaException(userMessage);
    }
}

