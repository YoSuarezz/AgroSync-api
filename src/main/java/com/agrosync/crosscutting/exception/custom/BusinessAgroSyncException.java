package com.agrosync.crosscutting.exception.custom;

import com.agrosync.crosscutting.exception.enums.Layer;

public final class BusinessAgroSyncException extends AgroSyncException {

    private static final long serialVersionUID = 1L;
    private static final Layer layer = Layer.BUSINESS;

    public BusinessAgroSyncException(final String mensajeUsuario) {
        super(mensajeUsuario, layer);
    }

    public BusinessAgroSyncException(final String mensajeTecnico, final String mensajeUsuario) {
        super(mensajeTecnico, mensajeUsuario, layer);
    }

    public BusinessAgroSyncException(final String mensajeTecnico, final String mensajeUsuario,
                                     final Throwable excepcionRaiz) {
        super(mensajeTecnico, mensajeUsuario, layer, excepcionRaiz);
    }
}