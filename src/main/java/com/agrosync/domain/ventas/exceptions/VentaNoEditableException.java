package com.agrosync.domain.ventas.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class VentaNoEditableException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private VentaNoEditableException(final String userMessage) {
        super(userMessage, userMessage, new Exception());
    }

    public static VentaNoEditableException create() {
        var userMessage = "La venta no puede ser editada porque fue anulada";
        return new VentaNoEditableException(userMessage);
    }
}
