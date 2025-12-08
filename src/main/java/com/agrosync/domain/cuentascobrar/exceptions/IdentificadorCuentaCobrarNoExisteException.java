package com.agrosync.domain.cuentascobrar.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class IdentificadorCuentaCobrarNoExisteException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private IdentificadorCuentaCobrarNoExisteException(final String userMessage) {
        super(userMessage, userMessage, new Exception());
    }

    public static IdentificadorCuentaCobrarNoExisteException create() {
        var userMessage = "El identificador de la Cuenta por Cobrar proporcionado no existe en el sistema.";
        return new IdentificadorCuentaCobrarNoExisteException(userMessage);
    }
}
