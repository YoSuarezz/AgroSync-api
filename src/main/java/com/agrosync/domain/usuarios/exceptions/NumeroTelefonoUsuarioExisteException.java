package com.agrosync.domain.usuarios.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class NumeroTelefonoUsuarioExisteException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private NumeroTelefonoUsuarioExisteException(final String userMessage) {
        super(userMessage, userMessage, new Exception());
    }

    public static NumeroTelefonoUsuarioExisteException create() {
        var userMessage = "El numero de telefono ya existe";
        return new NumeroTelefonoUsuarioExisteException(userMessage);
    }
}
