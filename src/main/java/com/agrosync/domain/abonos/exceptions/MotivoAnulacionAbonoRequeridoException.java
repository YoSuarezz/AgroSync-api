package com.agrosync.domain.abonos.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class MotivoAnulacionAbonoRequeridoException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private MotivoAnulacionAbonoRequeridoException(String technicalMessage, String userMessage) {
        super(technicalMessage, userMessage);
    }

    public static MotivoAnulacionAbonoRequeridoException create() {
        var userMessage = "Debe proporcionar un motivo para la anulación del abono.";
        return new MotivoAnulacionAbonoRequeridoException(userMessage, userMessage);
    }
}

