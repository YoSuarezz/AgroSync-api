package com.agrosync.domain.lotes.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class ContramarcaVaciaException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private ContramarcaVaciaException(final String userMessage) {
        super(userMessage, userMessage, new Exception());
    }

    public static ContramarcaVaciaException create() {
        var userMessage = "La contramarca del lote no puede ser vacía.";
        return new ContramarcaVaciaException(userMessage);
    }
}