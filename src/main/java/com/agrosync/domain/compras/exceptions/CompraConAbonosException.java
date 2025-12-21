package com.agrosync.domain.compras.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class CompraConAbonosException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private CompraConAbonosException(String technicalMessage, String userMessage) {
        super(technicalMessage, userMessage);
    }

    public static CompraConAbonosException create() {
        var userMessage = "No se puede anular la compra porque su cuenta por pagar ya tiene abonos registrados. Debe anular los abonos primero.";
        return new CompraConAbonosException(userMessage, userMessage);
    }
}

