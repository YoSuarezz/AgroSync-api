package com.agrosync.domain.suscripcion.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class FechaUltimoPagoRequeridaException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private FechaUltimoPagoRequeridaException(String mensajeUsuario) {
        super(mensajeUsuario, mensajeUsuario, new Exception());
    }

    public static FechaUltimoPagoRequeridaException create() {
        var mensajeUsuario = "La fecha del último pago es obligatoria para activar la suscripción";
        return new FechaUltimoPagoRequeridaException(mensajeUsuario);
    }
}
