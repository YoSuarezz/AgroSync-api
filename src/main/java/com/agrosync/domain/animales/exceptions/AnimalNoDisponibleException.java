package com.agrosync.domain.animales.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class AnimalNoDisponibleException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private AnimalNoDisponibleException(final String userMessage) {
        super(userMessage, userMessage, new Exception());
    }

    public static AnimalNoDisponibleException create() {
        var userMessage = "El animal no se encuentra disponible para actualizar su estado.";
        return new AnimalNoDisponibleException(userMessage);
    }
}
