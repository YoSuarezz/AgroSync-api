package com.agrosync.domain.abonos.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class IdentificadorAbonoNoExisteException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private IdentificadorAbonoNoExisteException(String technicalMessage, String userMessage) {
        super(technicalMessage, userMessage);
    }

    public static IdentificadorAbonoNoExisteException create() {
        var userMessage = "El identificador del abono proporcionado no existe en el sistema.";
        return new IdentificadorAbonoNoExisteException(userMessage, userMessage);
    }
}

