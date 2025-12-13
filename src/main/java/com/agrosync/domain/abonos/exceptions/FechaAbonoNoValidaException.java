package com.agrosync.domain.abonos.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class FechaAbonoNoValidaException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private FechaAbonoNoValidaException(final String userMessage) {
        super(userMessage, userMessage, new Exception());
    }

    public static FechaAbonoNoValidaException create() {
        var userMessage = "La fecha del abono debe estar entre los últimos 15 días y la fecha actual.";
        return new FechaAbonoNoValidaException(userMessage);
    }
}
