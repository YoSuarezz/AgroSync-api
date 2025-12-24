package com.agrosync.domain.animales.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class AnimalNoEditableException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private AnimalNoEditableException(final String userMessage) {
        super(userMessage, userMessage, new Exception());
    }

    public static AnimalNoEditableException create() {
        var userMessage = "El animal no puede ser editado porque fue anulado";
        return new AnimalNoEditableException(userMessage);
    }

    public static AnimalNoEditableException createPorVendido() {
        var userMessage = "El animal vendido no permite modificar peso ni sexo. Solo se permite editar el precio de compra.";
        return new AnimalNoEditableException(userMessage);
    }
}
