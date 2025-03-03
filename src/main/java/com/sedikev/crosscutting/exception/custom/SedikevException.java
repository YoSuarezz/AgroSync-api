package com.sedikev.crosscutting.exception.custom;

import com.sedikev.crosscutting.exception.enums.Layer;
import com.sedikev.crosscutting.helpers.ObjectHelper;
import com.sedikev.crosscutting.helpers.TextHelper;

public class SedikevException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    protected String mensajeUsuario;
    protected Layer layer;

    public SedikevException(final String mensajeTecnico, final String mensajeUsuario, final Layer layer,final Throwable excepcionRaiz) {
        super(mensajeTecnico, excepcionRaiz);
        setMensajeUsuario(mensajeUsuario);
        setLayer(layer);
    }

    public SedikevException(final String mensajeUsuario, final Layer layer) {
        super(mensajeUsuario);
        setMensajeUsuario(mensajeUsuario);
        setLayer(layer);
    }

    public SedikevException(String mensajeTecnico, String mensajeUsuario, Layer layer) {
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
