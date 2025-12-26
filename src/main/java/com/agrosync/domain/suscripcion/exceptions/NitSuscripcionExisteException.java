package com.agrosync.domain.suscripcion.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class NitSuscripcionExisteException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private NitSuscripcionExisteException(String mensajeUsuario) {
        super(mensajeUsuario, mensajeUsuario, new Exception());
    }

    public static NitSuscripcionExisteException create() {
        var mensajeUsuario = "Ya existe una suscripcion con el NIT proporcionado";
        return new NitSuscripcionExisteException(mensajeUsuario);
    }
}
