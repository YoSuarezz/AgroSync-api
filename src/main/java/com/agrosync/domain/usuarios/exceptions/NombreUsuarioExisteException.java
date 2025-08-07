package com.agrosync.domain.usuarios.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class NombreUsuarioExisteException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private NombreUsuarioExisteException(final String userMessage) {
        super(userMessage, userMessage, new Exception());
    }

    public static NombreUsuarioExisteException create() {
        var userMessage = "El nombre de usuario ya existe";
        return new NombreUsuarioExisteException(userMessage);
    }
}
