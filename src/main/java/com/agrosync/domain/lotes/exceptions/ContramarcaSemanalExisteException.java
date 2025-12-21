package com.agrosync.domain.lotes.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class ContramarcaSemanalExisteException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private ContramarcaSemanalExisteException(final String userMessage) {
        super(userMessage, userMessage, new Exception());
    }

    public static ContramarcaSemanalExisteException create() {
        var userMessage = "Ya existe un lote con esta contramarca en la misma semana y suscripción.";
        return new ContramarcaSemanalExisteException(userMessage);
    }
}