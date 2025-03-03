package com.sedikev.crosscutting.exception.custom;

import com.sedikev.crosscutting.exception.enums.Layer;
import com.sedikev.crosscutting.exception.SedikevException;

public class CrosscuttingSedikevException extends SedikevException{

    private static final long serialVersionUID = 1L;
    private static final Layer layer = Layer.CROSSCUTTING;

    public CrosscuttingSedikevException(final String mensajeUsuario) {
        super(mensajeUsuario, layer);
    }

    public CrosscuttingSedikevException(final String mensajeTecnico, final String mensajeUsuario) {
        super(mensajeTecnico, mensajeUsuario, layer);
    }

    public CrosscuttingSedikevException(final String mensajeTecnico, final String mensajeUsuario,
                                        final Throwable excepcionRaiz) {
        super(mensajeTecnico, mensajeUsuario, layer, excepcionRaiz);
    }
}
