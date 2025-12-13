package com.agrosync.domain.cobros.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class MontoCobroExcedeSaldoException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private MontoCobroExcedeSaldoException(final String userMessage) {
        super(userMessage, userMessage, new Exception());
    }

    public static MontoCobroExcedeSaldoException create() {
        var userMessage = "El monto del cobro no puede exceder el saldo pendiente de la cuenta.";
        return new MontoCobroExcedeSaldoException(userMessage);
    }
}
