package com.agrosync.domain.abonos.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class MontoAbonoNoValidoException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private MontoAbonoNoValidoException(final String userMessage) {
        super(userMessage, userMessage, new Exception());
    }

    public static MontoAbonoNoValidoException create() {
        var userMessage = "El monto del abono debe ser mayor a cero.";
        return new MontoAbonoNoValidoException(userMessage);
    }
}
