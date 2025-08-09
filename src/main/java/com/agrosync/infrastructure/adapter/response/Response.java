package com.agrosync.infrastructure.adapter.response;

import java.util.ArrayList;
import java.util.List;

public class Response<T> {

    private List<String> mensajes = new ArrayList<>();
    private Object datos;

    public List<String> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<String> mensajes) {
        this.mensajes = mensajes;
    }

    public Object getDatos() {
        return datos;
    }

    public void setDatos(Object datos) {
        this.datos = datos;
    }
}