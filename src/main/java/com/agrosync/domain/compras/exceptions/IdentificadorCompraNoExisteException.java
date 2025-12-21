package com.agrosync.domain.compras.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class IdentificadorCompraNoExisteException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private IdentificadorCompraNoExisteException(final String userMessage) {
        super(userMessage, userMessage, new Exception());
    }

    public static IdentificadorCompraNoExisteException create() {
        var userMessage = "El identificador de la Compra proporcionado no existe en el sistema.";
        return new IdentificadorCompraNoExisteException(userMessage);
    }
}
