package com.agrosync.infrastructure.primaryadapters.adapter.response.compras;

import com.agrosync.infrastructure.primaryadapters.adapter.response.ResponseWithData;

import java.util.List;

public class CompraResponse<T> extends ResponseWithData<T> {

    public static <T> CompraResponse<T> build(final List<String> mensajes, final T datos) {
        CompraResponse<T> response = new CompraResponse<>();
        response.setMensajes(mensajes);
        response.setDatos(datos);
        return response;
    }
}
