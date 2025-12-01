package com.agrosync.infrastructure.primaryadapters.adapter.response.usuarios;

import com.agrosync.infrastructure.primaryadapters.adapter.response.ResponseWithData;

import java.util.List;

public class UsuarioResponse<T> extends ResponseWithData<T> {

    public static <T> UsuarioResponse<T> build(final List<String> mensajes, final T datos) {
        UsuarioResponse<T> response = new UsuarioResponse<>();
        response.setMensajes(mensajes);
        response.setDatos(datos);
        return response;
    }
}
