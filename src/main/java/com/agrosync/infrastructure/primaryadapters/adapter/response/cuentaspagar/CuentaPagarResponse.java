package com.agrosync.infrastructure.primaryadapters.adapter.response.cuentaspagar;

import com.agrosync.infrastructure.primaryadapters.adapter.response.ResponseWithData;

import java.util.List;

public class CuentaPagarResponse<T> extends ResponseWithData<T> {

    public static <T> CuentaPagarResponse<T> build(final List<String> mensajes, final T datos) {
        CuentaPagarResponse<T> response = new CuentaPagarResponse<>();
        response.setMensajes(mensajes);
        response.setDatos(datos);
        return response;
    }
}