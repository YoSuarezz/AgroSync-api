package com.agrosync.domain.animales.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class NumeroAnimalDuplicadoException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private NumeroAnimalDuplicadoException(final String userMessage) {
        super(userMessage, userMessage, new Exception());
    }

    public static NumeroAnimalDuplicadoException create() {
        var userMessage = "El número identificador del animal ya existe.";
        return new NumeroAnimalDuplicadoException(userMessage);
    }
}
