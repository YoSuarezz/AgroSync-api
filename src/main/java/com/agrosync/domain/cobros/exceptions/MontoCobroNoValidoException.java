package com.agrosync.domain.cobros.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class MontoCobroNoValidoException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private MontoCobroNoValidoException(final String userMessage) {
        super(userMessage, userMessage, new Exception());
    }

    public static MontoCobroNoValidoException create() {
        var userMessage = "El monto del cobro debe ser mayor a cero.";
        return new MontoCobroNoValidoException(userMessage);
    }
}
