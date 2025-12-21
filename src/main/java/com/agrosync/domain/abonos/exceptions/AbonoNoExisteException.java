package com.agrosync.domain.abonos.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class AbonoNoExisteException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private AbonoNoExisteException(final String userMessage) {
        super(userMessage, userMessage, new Exception());
    }

    public static AbonoNoExisteException create() {
        var userMessage = "El abono solicitado no existe en el sistema.";
        return new AbonoNoExisteException(userMessage);
    }
}
