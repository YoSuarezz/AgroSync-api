package com.sedikev.crosscutting.exception.custom;

import com.sedikev.crosscutting.exception.enums.Layer;

public final class InitializerSedikevException extends SedikevException {

    private static final long serialVersionUID = 1L;
    private static final Layer layer = Layer.INITIALIZER;

    public InitializerSedikevException(final String mensajeUsuario) {
        super(mensajeUsuario, layer);
    }

    public InitializerSedikevException(final String mensajeTecnico, final String mensajeUsuario) {
        super(mensajeTecnico, mensajeUsuario, layer);
    }

    public InitializerSedikevException(final String mensajeTecnico, final String mensajeUsuario,
                                       final Throwable excepcionRaiz) {
        super(mensajeTecnico, mensajeUsuario, layer, excepcionRaiz);
    }
}