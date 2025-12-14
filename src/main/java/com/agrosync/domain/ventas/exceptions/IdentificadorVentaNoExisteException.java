package com.agrosync.domain.ventas.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class IdentificadorVentaNoExisteException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private IdentificadorVentaNoExisteException(final String userMessage) {
        super(userMessage, userMessage, new Exception());
    }

    public static IdentificadorVentaNoExisteException create() {
        var userMessage = "El identificador de la Venta proporcionado no existe en el sistema.";
        return new IdentificadorVentaNoExisteException(userMessage);
    }
}
