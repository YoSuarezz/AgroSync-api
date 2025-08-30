package com.agrosync.domain.usuarios.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class CaracteresNumeroTelefonoUsuarioNoValidosException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private CaracteresNumeroTelefonoUsuarioNoValidosException(final String userMessage) {
        super(userMessage, userMessage, new Exception());
    }

    public static CaracteresNumeroTelefonoUsuarioNoValidosException create() {
        var userMessage = "El numero de teléfono debe tener exactamente 10 caracteres";
        return new CaracteresNumeroTelefonoUsuarioNoValidosException(userMessage);
    }
}
