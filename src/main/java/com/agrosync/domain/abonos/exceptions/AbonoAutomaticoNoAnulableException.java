package com.agrosync.domain.abonos.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class AbonoAutomaticoNoAnulableException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private AbonoAutomaticoNoAnulableException(String technicalMessage, String userMessage) {
        super(technicalMessage, userMessage);
    }

    public static AbonoAutomaticoNoAnulableException create() {
        var userMessage = "No se puede anular un abono generado automáticamente por cruce de cuentas. Para revertir este pago, anule la compra que lo originó.";
        return new AbonoAutomaticoNoAnulableException(userMessage, userMessage);
    }
}
