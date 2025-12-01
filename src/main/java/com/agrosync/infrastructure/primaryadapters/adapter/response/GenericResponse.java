package com.agrosync.infrastructure.primaryadapters.adapter.response;

import java.util.List;

public class GenericResponse extends Response {

    public static GenericResponse build(final List<String> mensajes) {
        GenericResponse response = new GenericResponse();
        response.setMensajes(mensajes);
        return response;
    }
}
