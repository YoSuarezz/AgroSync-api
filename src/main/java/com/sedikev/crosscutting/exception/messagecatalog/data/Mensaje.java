package com.sedikev.crosscutting.exception.messagecatalog.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
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
}
