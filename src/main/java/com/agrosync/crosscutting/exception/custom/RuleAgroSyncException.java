package com.agrosync.crosscutting.exception.custom;

import com.agrosync.crosscutting.exception.enums.Layer;

public class RuleAgroSyncException extends AgroSyncException {

    private static final long serialVersionUID = 1L;
    private static final Layer layer = Layer.RULE;

    public RuleAgroSyncException(final String mensajeUsuario) {
        super(mensajeUsuario, layer);
    }

    public RuleAgroSyncException(final String mensajeTecnico, final String mensajeUsuario) {
        super(mensajeTecnico, mensajeUsuario, layer);
    }

    public RuleAgroSyncException(final String mensajeTecnico, final String mensajeUsuario,
                                 final Throwable excepcionRaiz) {
        super(mensajeTecnico, mensajeUsuario, layer, excepcionRaiz);
    }
}