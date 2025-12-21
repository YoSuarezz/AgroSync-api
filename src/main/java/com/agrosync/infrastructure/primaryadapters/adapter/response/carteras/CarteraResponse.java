package com.agrosync.infrastructure.primaryadapters.adapter.response.carteras;

import com.agrosync.infrastructure.primaryadapters.adapter.response.ResponseWithData;

import java.util.List;

public class CarteraResponse<T> extends ResponseWithData<T> {

    public static <T> CarteraResponse<T> build(final List<String> mensajes, final T datos) {
        CarteraResponse<T> response = new CarteraResponse<>();
        response.setMensajes(mensajes);
        response.setDatos(datos);
        return response;
    }
}
