package com.agrosync.domain.usuarios.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class UsuarioIdNoExisteException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private UsuarioIdNoExisteException(final String userMessage) {
        super(userMessage, userMessage, new Exception());
    }

    public static UsuarioIdNoExisteException create() {
        var userMessage = "El usuario no existe";
        return new UsuarioIdNoExisteException(userMessage);
    }
}
