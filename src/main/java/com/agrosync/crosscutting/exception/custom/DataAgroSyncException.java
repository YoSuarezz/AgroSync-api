package com.agrosync.crosscutting.exception.custom;

import com.agrosync.crosscutting.exception.enums.Layer;

public final class DataAgroSyncException extends AgroSyncException {

    private static final long serialVersionUID = 1L;
    private static final Layer layer = Layer.DATA;

    public DataAgroSyncException(final String mensajeUsuario) {
        super(mensajeUsuario, layer);
    }

    public DataAgroSyncException(final String mensajeTecnico, final String mensajeUsuario) {
        super(mensajeTecnico, mensajeUsuario, layer);
    }

    public DataAgroSyncException(final String mensajeTecnico, final String mensajeUsuario,
                                 final Throwable excepcionRaiz) {
        super(mensajeTecnico, mensajeUsuario, layer, excepcionRaiz);
    }
}