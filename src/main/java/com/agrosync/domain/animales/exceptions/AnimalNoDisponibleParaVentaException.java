package com.agrosync.domain.animales.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class AnimalNoDisponibleParaVentaException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private AnimalNoDisponibleParaVentaException(final String userMessage) {
        super(userMessage, userMessage, new Exception());
    }

    public static AnimalNoDisponibleParaVentaException create() {
        var userMessage = "El animal seleccionado no está disponible para la venta.";
        return new AnimalNoDisponibleParaVentaException(userMessage);
    }
}
