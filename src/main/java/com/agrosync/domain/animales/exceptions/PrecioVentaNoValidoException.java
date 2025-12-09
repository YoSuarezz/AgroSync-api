package com.agrosync.domain.animales.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

import java.math.BigDecimal;

public class PrecioVentaNoValidoException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private PrecioVentaNoValidoException(final String userMessage) {
        super(userMessage, userMessage, new Exception());
    }

    public static PrecioVentaNoValidoException create() {
        var msg = "El precio por kilo de venta debe ser mayor a 0";
        return new PrecioVentaNoValidoException(msg);
    }
}
