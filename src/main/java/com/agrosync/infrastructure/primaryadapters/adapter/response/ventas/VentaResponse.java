package com.agrosync.infrastructure.primaryadapters.adapter.response.ventas;

import com.agrosync.infrastructure.primaryadapters.adapter.response.ResponseWithData;

import java.util.List;

public class VentaResponse<T> extends ResponseWithData<T> {

    public static <T> VentaResponse<T> build(final List<String> mensajes, final T datos) {
        VentaResponse<T> response = new VentaResponse<>();
        response.setMensajes(mensajes);
        response.setDatos(datos);
        return response;
    }
}
