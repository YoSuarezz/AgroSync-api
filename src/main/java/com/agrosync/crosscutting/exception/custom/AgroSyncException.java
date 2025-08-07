package com.agrosync.crosscutting.exception.custom;

import com.agrosync.crosscutting.exception.enums.Layer;
import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.crosscutting.helpers.TextHelper;

public class AgroSyncException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    protected String mensajeUsuario;
    protected Layer layer;

    public AgroSyncException(final String mensajeTecnico, final String mensajeUsuario, final Layer layer, final Throwable excepcionRaiz) {
        super(mensajeTecnico, excepcionRaiz);
        setMensajeUsuario(mensajeUsuario);
        setLayer(layer);
    }

    public AgroSyncException(final String mensajeUsuario, final Layer layer) {
        super(mensajeUsuario);
        setMensajeUsuario(mensajeUsuario);
        setLayer(layer);
    }

    public AgroSyncException(String mensajeTecnico, String mensajeUsuario, Layer layer) {
        super(mensajeTecnico);
        setMensajeUsuario(mensajeUsuario);
        setLayer(layer);
    }

    private final void setMensajeUsuario(final String mensajeUsuario) {
        this.mensajeUsuario = TextHelper.applyTrim(mensajeUsuario);
    }

    private final void setLayer(final Layer layer) {
        this.layer = ObjectHelper.getObjectHelper().getDefault(layer, Layer.DEFAULT);
    }

    public final String getMensajeUsuario() {
        return mensajeUsuario;
    }

    public final Layer getLayer() {
        return layer;
    }
}
