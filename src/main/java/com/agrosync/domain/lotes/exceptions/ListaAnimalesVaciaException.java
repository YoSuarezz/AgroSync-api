package com.agrosync.domain.lotes.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class ListaAnimalesVaciaException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private ListaAnimalesVaciaException(final String userMessage) {
        super(userMessage, userMessage, new Exception());
    }

    public static ListaAnimalesVaciaException create() {
        var userMessage = "El lote debe contener al menos un animal.";
        return new ListaAnimalesVaciaException(userMessage);
    }
}