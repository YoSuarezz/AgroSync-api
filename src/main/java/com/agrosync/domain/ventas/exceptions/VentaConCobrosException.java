package com.agrosync.domain.ventas.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class VentaConCobrosException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private VentaConCobrosException(String technicalMessage, String userMessage) {
        super(technicalMessage, userMessage);
    }

    public static VentaConCobrosException create() {
        var userMessage = "No se puede anular la venta porque su cuenta por cobrar ya tiene cobros registrados. Debe anular los cobros primero.";
        return new VentaConCobrosException(userMessage, userMessage);
    }
}

