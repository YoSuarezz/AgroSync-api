package com.agrosync.infrastructure.primaryadapters.adapter.response.cuentascobrar;

import com.agrosync.infrastructure.primaryadapters.adapter.response.ResponseWithData;

import java.util.List;

public class CuentaCobrarResponse<T> extends ResponseWithData<T> {

    public static <T> CuentaCobrarResponse<T> build(final List<String> mensajes, final T datos) {
        CuentaCobrarResponse<T> response = new CuentaCobrarResponse<>();
        response.setMensajes(mensajes);
        response.setDatos(datos);
        return response;
    }
}