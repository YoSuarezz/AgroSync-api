package com.agrosync.domain.cobros.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class CobroYaAnuladoException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private CobroYaAnuladoException(String technicalMessage, String userMessage) {
        super(technicalMessage, userMessage);
    }

    public static CobroYaAnuladoException create() {
        var userMessage = "El cobro ya se encuentra anulado y no puede ser anulado nuevamente.";
        return new CobroYaAnuladoException(userMessage, userMessage);
    }
}

