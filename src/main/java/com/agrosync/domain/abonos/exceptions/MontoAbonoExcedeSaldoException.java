package com.agrosync.domain.abonos.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class MontoAbonoExcedeSaldoException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private MontoAbonoExcedeSaldoException(final String userMessage) {
        super(userMessage, userMessage, new Exception());
    }

    public static MontoAbonoExcedeSaldoException create() {
        var userMessage = "El monto del abono no puede exceder el saldo pendiente de la cuenta.";
        return new MontoAbonoExcedeSaldoException(userMessage);
    }
}
