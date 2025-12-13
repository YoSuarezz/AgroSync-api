package com.agrosync.infrastructure.primaryadapters.adapter.response.abonos;

import com.agrosync.infrastructure.primaryadapters.adapter.response.ResponseWithData;

import java.util.List;

public class AbonoResponse<T> extends ResponseWithData<T> {

    public static <T> AbonoResponse<T> build(final List<String> mensajes, final T datos) {
        AbonoResponse<T> response = new AbonoResponse<>();
        response.setMensajes(mensajes);
        response.setDatos(datos);
        return response;
    }
}
