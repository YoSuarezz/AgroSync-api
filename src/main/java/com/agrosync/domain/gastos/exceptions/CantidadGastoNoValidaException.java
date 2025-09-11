package com.agrosync.domain.gastos.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class CantidadGastoNoValidaException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private CantidadGastoNoValidaException(final String userMessage) {
        super(userMessage, userMessage, new Exception());
    }

    public static CantidadGastoNoValidaException create() {
        var userMessage = "La cantidad ingresada no puede ser negativa o Cero";
        return new CantidadGastoNoValidaException(userMessage);
    }

}
