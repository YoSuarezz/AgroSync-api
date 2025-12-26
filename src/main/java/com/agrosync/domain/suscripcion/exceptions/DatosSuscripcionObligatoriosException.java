package com.agrosync.domain.suscripcion.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class DatosSuscripcionObligatoriosException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private DatosSuscripcionObligatoriosException(String mensajeUsuario) {
        super(mensajeUsuario, mensajeUsuario, new Exception());
    }

    public static DatosSuscripcionObligatoriosException create() {
        var mensajeUsuario = "El nombre, NIT y email de la suscripcion son requeridos";
        return new DatosSuscripcionObligatoriosException(mensajeUsuario);
    }
}
