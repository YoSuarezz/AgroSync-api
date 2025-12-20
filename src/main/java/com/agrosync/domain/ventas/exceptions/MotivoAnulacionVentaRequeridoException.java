package com.agrosync.domain.ventas.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class MotivoAnulacionVentaRequeridoException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private MotivoAnulacionVentaRequeridoException(String technicalMessage, String userMessage) {
        super(technicalMessage, userMessage);
    }

    public static MotivoAnulacionVentaRequeridoException create() {
        var userMessage = "Debe proporcionar un motivo para la anulación de la venta.";
        return new MotivoAnulacionVentaRequeridoException(userMessage, userMessage);
    }
}

