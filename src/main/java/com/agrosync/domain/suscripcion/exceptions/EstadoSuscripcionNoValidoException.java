package com.agrosync.domain.suscripcion.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class EstadoSuscripcionNoValidoException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private EstadoSuscripcionNoValidoException(String mensajeUsuario) {
        super(mensajeUsuario, mensajeUsuario, new Exception());
    }

    public static EstadoSuscripcionNoValidoException create() {
        var mensajeUsuario = "El estado de la suscripcion es requerido";
        return new EstadoSuscripcionNoValidoException(mensajeUsuario);
    }
}
