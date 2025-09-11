package com.agrosync.domain.gastos.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class GastoCaracteresDescripcionNoValidosException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private GastoCaracteresDescripcionNoValidosException(String userMessage) {
        super(userMessage, userMessage, new Exception());
    }

    public static GastoCaracteresDescripcionNoValidosException create() {
        var userMessage = "La descripción no puede tener más de 250 carácteres";
        return new GastoCaracteresDescripcionNoValidosException(userMessage);
    }
}
