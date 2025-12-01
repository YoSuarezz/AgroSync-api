package com.agrosync.crosscutting.exception.messagecatalog.data;

public final class Mensaje {

    private final CodigoMensaje codigo;
    private final String contenido;

    public boolean esBase() {
        return codigo.isBase();
    }

    public CategoriaMensaje getCategoria() {
        return codigo.getCategoria();
    }

    public TipoMensaje getTipo() {
        return codigo.getTipo();
    }

    public String getIdentificador() {
        return codigo.getIdentificador();
    }

    public Mensaje(CodigoMensaje codigo, String contenido) {
        this.codigo = codigo;
        this.contenido = contenido;
    }

    public CodigoMensaje getCodigo() {
        return codigo;
    }

    public String getContenido() {
        return contenido;
    }


}
