package com.agrosync.domain.compras.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class CompraYaAnuladaException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private CompraYaAnuladaException(String technicalMessage, String userMessage) {
        super(technicalMessage, userMessage);
    }

    public static CompraYaAnuladaException create() {
        var userMessage = "La compra ya se encuentra anulada y no puede ser anulada nuevamente.";
        return new CompraYaAnuladaException(userMessage, userMessage);
    }
}

