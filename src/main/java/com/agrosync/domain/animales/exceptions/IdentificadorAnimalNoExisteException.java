package com.agrosync.domain.animales.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class IdentificadorAnimalNoExisteException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private IdentificadorAnimalNoExisteException(final String userMessage) {
        super(userMessage, userMessage, new Exception());
    }

    public static IdentificadorAnimalNoExisteException create() {
        var userMessage = "El identificador del animal proporcionado no existe en el sistema.";
        return new IdentificadorAnimalNoExisteException(userMessage);
    }
}