package com.agrosync.domain.cuentaspagar.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class IdentificadorCuentaPagarNoExisteException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private IdentificadorCuentaPagarNoExisteException(final String userMessage) {
        super(userMessage, userMessage, new Exception());
    }

    public static IdentificadorCuentaPagarNoExisteException create() {
        var userMessage = "El identificador de la Cuenta por Pagar proporcionado no existe en el sistema.";
        return new IdentificadorCuentaPagarNoExisteException(userMessage);
    }
}
