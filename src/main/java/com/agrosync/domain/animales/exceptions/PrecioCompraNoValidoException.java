package com.agrosync.domain.animales.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;
import java.math.BigDecimal;

public class PrecioCompraNoValidoException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private PrecioCompraNoValidoException(final String userMessage) {
        super(userMessage, userMessage, new Exception());
    }

    public static PrecioCompraNoValidoException create(BigDecimal precio) {
        var msg = "El precio por kilo de compra debe ser mayor a 0. Valor recibido: " + precio;
        return new PrecioCompraNoValidoException(msg);
    }
}
