package com.agrosync.domain.animales.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class CaracteresSlotNoValidosException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private CaracteresSlotNoValidosException(final String userMessage) {
        super(userMessage, userMessage, new Exception());
    }

    public static CaracteresSlotNoValidosException create() {
        var msg = "El slot del animal no es válido. Debe ser numérico y tener menos de 20 caracteres.";
        return new CaracteresSlotNoValidosException(msg);
    }
}
