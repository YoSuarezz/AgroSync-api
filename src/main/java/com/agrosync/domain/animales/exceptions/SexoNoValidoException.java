package com.agrosync.domain.animales.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class SexoNoValidoException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private SexoNoValidoException(String userMessage) {
        super(userMessage, userMessage, new Exception());
    }

    public static SexoNoValidoException create() {
        return new SexoNoValidoException("El sexo del animal es obligatorio.");
    }
}
