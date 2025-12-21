package com.agrosync.domain.animales.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;
import java.math.BigDecimal;

public class PesoNoValidoException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private PesoNoValidoException(final String userMessage) {
        super(userMessage, userMessage, new Exception());
    }

    public static PesoNoValidoException create(BigDecimal peso) {
        var msg = "El peso del animal debe ser mayor a 0. Valor recibido: " + peso;
        return new PesoNoValidoException(msg);
    }
}
