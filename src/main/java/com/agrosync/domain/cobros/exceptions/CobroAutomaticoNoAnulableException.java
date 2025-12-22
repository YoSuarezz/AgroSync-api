package com.agrosync.domain.cobros.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class CobroAutomaticoNoAnulableException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private CobroAutomaticoNoAnulableException(String technicalMessage, String userMessage) {
        super(technicalMessage, userMessage);
    }

    public static CobroAutomaticoNoAnulableException create() {
        var userMessage = "No se puede anular un cobro generado automáticamente por cruce de cuentas. Para revertir este pago, anule la venta que lo originó.";
        return new CobroAutomaticoNoAnulableException(userMessage, userMessage);
    }
}
