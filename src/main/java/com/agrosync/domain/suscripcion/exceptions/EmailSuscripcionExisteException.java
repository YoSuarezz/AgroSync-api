package com.agrosync.domain.suscripcion.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class EmailSuscripcionExisteException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private EmailSuscripcionExisteException(String mensajeUsuario) {
        super(mensajeUsuario, mensajeUsuario, new Exception());
    }

    public static EmailSuscripcionExisteException create() {
        var mensajeUsuario = "Ya existe una suscripcion con el email proporcionado";
        return new EmailSuscripcionExisteException(mensajeUsuario);
    }
}
