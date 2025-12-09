package com.agrosync.domain.ventas.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class VentaSinAnimalesException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private VentaSinAnimalesException(final String userMessage) {
        super(userMessage, userMessage, new Exception());
    }

    public static VentaSinAnimalesException create() {
        var userMessage = "La venta debe incluir al menos un animal.";
        return new VentaSinAnimalesException(userMessage);
    }
}
