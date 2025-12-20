package com.agrosync.domain.cobros.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class MotivoAnulacionCobroRequeridoException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private MotivoAnulacionCobroRequeridoException(String technicalMessage, String userMessage) {
        super(technicalMessage, userMessage);
    }

    public static MotivoAnulacionCobroRequeridoException create() {
        var userMessage = "Debe proporcionar un motivo para la anulación del cobro.";
        return new MotivoAnulacionCobroRequeridoException(userMessage, userMessage);
    }
}

