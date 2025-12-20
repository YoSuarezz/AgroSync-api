package com.agrosync.domain.abonos.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class AbonoYaAnuladoException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private AbonoYaAnuladoException(String technicalMessage, String userMessage) {
        super(technicalMessage, userMessage);
    }

    public static AbonoYaAnuladoException create() {
        var userMessage = "El abono ya se encuentra anulado y no puede ser anulado nuevamente.";
        return new AbonoYaAnuladoException(userMessage, userMessage);
    }
}

