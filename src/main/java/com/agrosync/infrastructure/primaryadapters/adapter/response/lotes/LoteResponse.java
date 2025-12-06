package com.agrosync.infrastructure.primaryadapters.adapter.response.lotes;

import com.agrosync.infrastructure.primaryadapters.adapter.response.ResponseWithData;

import java.util.List;

public class LoteResponse<T> extends ResponseWithData<T> {

    public static <T> LoteResponse<T> build(final List<String> mensajes, final T datos) {
        LoteResponse<T> response = new LoteResponse<>();
        response.setMensajes(mensajes);
        response.setDatos(datos);
        return response;
    }
}
