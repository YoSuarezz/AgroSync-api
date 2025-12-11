package com.agrosync.infrastructure.primaryadapters.adapter.response.cobros;

import com.agrosync.infrastructure.primaryadapters.adapter.response.ResponseWithData;

import java.util.List;

public class CobroResponse<T> extends ResponseWithData<T> {

    public static <T> CobroResponse<T> build(final List<String> mensajes, final T datos) {
        CobroResponse<T> response = new CobroResponse<>();
        response.setMensajes(mensajes);
        response.setDatos(datos);
        return response;
    }
}
