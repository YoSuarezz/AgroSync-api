package com.agrosync.domain.gastos.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class GastoIdNoExisteException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private GastoIdNoExisteException(final String userMessage) {
        super(userMessage, userMessage, new Exception());
    }

    public static GastoIdNoExisteException create() {
        var userMessage = "El gasto no ha sido encontrado";
        return new GastoIdNoExisteException(userMessage);
    }
}
