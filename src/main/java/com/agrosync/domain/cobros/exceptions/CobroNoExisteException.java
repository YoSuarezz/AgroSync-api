package com.agrosync.domain.cobros.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class CobroNoExisteException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private CobroNoExisteException(final String userMessage) {
        super(userMessage, userMessage, new Exception());
    }

    public static CobroNoExisteException create() {
        var userMessage = "El cobro solicitado no existe en el sistema.";
        return new CobroNoExisteException(userMessage);
    }
}
