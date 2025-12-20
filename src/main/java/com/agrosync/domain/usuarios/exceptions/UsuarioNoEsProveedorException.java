package com.agrosync.domain.usuarios.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class UsuarioNoEsProveedorException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private UsuarioNoEsProveedorException(final String userMessage) {
        super(userMessage, userMessage, new Exception());
    }

    public static UsuarioNoEsProveedorException create() {
        var userMessage = "El usuario seleccionado para la compra debe ser de tipo PROVEEDOR o AMBOS.";
        return new UsuarioNoEsProveedorException(userMessage);
    }
}

