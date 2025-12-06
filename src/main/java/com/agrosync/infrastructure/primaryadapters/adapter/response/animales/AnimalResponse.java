package com.agrosync.infrastructure.primaryadapters.adapter.response.animales;

import com.agrosync.infrastructure.primaryadapters.adapter.response.ResponseWithData;

import java.util.List;

public class AnimalResponse<T> extends ResponseWithData<T> {

    public static <T> AnimalResponse<T> build(final List<String> mensajes, final T datos) {
        AnimalResponse<T> response = new AnimalResponse<>();
        response.setMensajes(mensajes);
        response.setDatos(datos);
        return response;
    }
}
