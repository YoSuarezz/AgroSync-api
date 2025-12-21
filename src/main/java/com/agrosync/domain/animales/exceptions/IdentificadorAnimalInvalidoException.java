package com.agrosync.domain.animales.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class IdentificadorAnimalInvalidoException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private IdentificadorAnimalInvalidoException(final String userMessage) {
        super(userMessage, userMessage, new Exception());
    }

    public static IdentificadorAnimalInvalidoException create() {
        var userMessage = "El identificador del animal debe ser un formato UUID válido.";
        return new IdentificadorAnimalInvalidoException(userMessage);
    }
}