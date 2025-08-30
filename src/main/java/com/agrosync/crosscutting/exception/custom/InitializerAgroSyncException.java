package com.agrosync.crosscutting.exception.custom;

import com.agrosync.crosscutting.exception.enums.Layer;

public final class InitializerAgroSyncException extends AgroSyncException {

    private static final long serialVersionUID = 1L;
    private static final Layer layer = Layer.INITIALIZER;

    public InitializerAgroSyncException(final String mensajeUsuario) {
        super(mensajeUsuario, layer);
    }

    public InitializerAgroSyncException(final String mensajeTecnico, final String mensajeUsuario) {
        super(mensajeTecnico, mensajeUsuario, layer);
    }

    public InitializerAgroSyncException(final String mensajeTecnico, final String mensajeUsuario,
                                        final Throwable excepcionRaiz) {
        super(mensajeTecnico, mensajeUsuario, layer, excepcionRaiz);
    }
}