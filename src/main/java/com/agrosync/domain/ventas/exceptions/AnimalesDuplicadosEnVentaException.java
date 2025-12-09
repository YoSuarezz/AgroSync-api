package com.agrosync.domain.ventas.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class AnimalesDuplicadosEnVentaException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private AnimalesDuplicadosEnVentaException(final String userMessage) {
        super(userMessage, userMessage, new Exception());
    }

    public static AnimalesDuplicadosEnVentaException create() {
        var userMessage = "No es posible agregar el mismo animal dos veces a la venta";
        return new AnimalesDuplicadosEnVentaException(userMessage);
    }
}
