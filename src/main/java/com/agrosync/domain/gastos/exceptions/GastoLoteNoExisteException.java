package com.agrosync.domain.gastos.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class GastoLoteNoExisteException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private GastoLoteNoExisteException(String userMessage) {
        super(userMessage, userMessage, new Exception());
    }

    public static GastoLoteNoExisteException create() {
        var userMessage = "El lote asociado al gasto no existe";
        return new GastoLoteNoExisteException(userMessage);
    }
}
