package com.agrosync.domain.compras.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class MotivoAnulacionRequeridoException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private MotivoAnulacionRequeridoException(String technicalMessage, String userMessage) {
        super(technicalMessage, userMessage);
    }

    public static MotivoAnulacionRequeridoException create() {
        var userMessage = "Debe proporcionar un motivo para la anulación.";
        return new MotivoAnulacionRequeridoException(userMessage, userMessage);
    }
}

