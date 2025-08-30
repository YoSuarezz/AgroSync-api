package com.agrosync.domain.usuarios.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class CaracteresNombreUsuarioNoValidosException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private CaracteresNombreUsuarioNoValidosException(final String userMessage) {
        super(userMessage, userMessage, new Exception());
    }

    public static CaracteresNombreUsuarioNoValidosException create() {
        var userMessage = "El nombre debe de tener menos de 50 caracteres";
        return new CaracteresNombreUsuarioNoValidosException(userMessage);
    }
}
