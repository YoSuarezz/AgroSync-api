package com.agrosync.domain.suscripcion.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class SuscripcionNoExisteException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private SuscripcionNoExisteException(String mensajeUsuario) {
        super(mensajeUsuario, mensajeUsuario, new Exception());
    }

    public static SuscripcionNoExisteException create() {
        var mensajeUsuario = "La suscripción no existe";
        return new SuscripcionNoExisteException(mensajeUsuario);
    }
}
