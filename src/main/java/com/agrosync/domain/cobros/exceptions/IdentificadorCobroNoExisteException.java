package com.agrosync.domain.cobros.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class IdentificadorCobroNoExisteException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private IdentificadorCobroNoExisteException(String technicalMessage, String userMessage) {
        super(technicalMessage, userMessage);
    }

    public static IdentificadorCobroNoExisteException create() {
        var userMessage = "El identificador del cobro proporcionado no existe en el sistema.";
        return new IdentificadorCobroNoExisteException(userMessage, userMessage);
    }
}

