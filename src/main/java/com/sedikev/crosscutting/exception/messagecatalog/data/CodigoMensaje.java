package com.sedikev.crosscutting.exception.messagecatalog.data;

import lombok.Getter;

import static com.sedikev.crosscutting.helpers.TextHelper.UNDERLINE;
import static com.sedikev.crosscutting.helpers.TextHelper.concatenate;

@Getter
public enum CodigoMensaje {

    M00001(TipoMensaje.TECNICO, CategoriaMensaje.ERROR, "00001", true),
    M00002(TipoMensaje.USUARIO, CategoriaMensaje.ERROR, "00002", true),
    M00003(TipoMensaje.TECNICO, CategoriaMensaje.ERROR, "00003", true),
    M00004(TipoMensaje.TECNICO, CategoriaMensaje.ERROR, "00004", true),
    M00005(TipoMensaje.TECNICO, CategoriaMensaje.ERROR, "00005", true),
    M00006(TipoMensaje.TECNICO, CategoriaMensaje.ERROR, "00006", true),
    M00007(TipoMensaje.TECNICO, CategoriaMensaje.ERROR, "00007", true);

    private final TipoMensaje tipo;
    private final CategoriaMensaje categoria;
    private final String codigo;
    private final boolean base;

    private CodigoMensaje(final TipoMensaje tipo, final CategoriaMensaje categoria, final String codigo, final boolean base) {
        this.tipo = tipo;
        this.categoria = categoria;
        this.codigo = codigo;
        this.base = base;
    }

    public String getIdentificador() {
        return concatenate(tipo.name(), UNDERLINE, categoria.name(), UNDERLINE, codigo);
    }
}