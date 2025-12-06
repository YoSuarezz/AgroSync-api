package com.agrosync.domain.animales.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class LoteNoValidoException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private LoteNoValidoException(String userMessage) {
        super(userMessage, userMessage, new Exception());
    }

    public static LoteNoValidoException create() {
        return new LoteNoValidoException("El animal debe pertenecer a un lote válido.");
    }
}
