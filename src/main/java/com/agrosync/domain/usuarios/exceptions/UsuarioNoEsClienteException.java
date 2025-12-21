package com.agrosync.domain.usuarios.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class UsuarioNoEsClienteException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private UsuarioNoEsClienteException(final String userMessage) {
        super(userMessage, userMessage, new Exception());
    }

    public static UsuarioNoEsClienteException create() {
        var userMessage = "El usuario seleccionado para la venta debe ser de tipo CLIENTE o AMBOS.";
        return new UsuarioNoEsClienteException(userMessage);
    }
}
