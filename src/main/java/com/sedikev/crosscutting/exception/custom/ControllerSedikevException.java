package com.sedikev.crosscutting.exception.custom;

import com.sedikev.crosscutting.exception.enums.Layer;

public final class ControllerSedikevException extends SedikevException {

    private static final long serialVersionUID = 1L;
    private static final Layer layer = Layer.CONTROLLER;

    public ControllerSedikevException(final String mensajeUsuario) {
        super(mensajeUsuario, layer);
    }

    public ControllerSedikevException(final String mensajeTecnico, final String mensajeUsuario) {
        super(mensajeTecnico, mensajeUsuario, layer);
    }

    public ControllerSedikevException(final String mensajeTecnico, final String mensajeUsuario,
                                      final Throwable excepcionRaiz) {
        super(mensajeTecnico, mensajeUsuario, layer, excepcionRaiz);
    }
}