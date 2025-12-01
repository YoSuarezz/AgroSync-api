package com.agrosync.infrastructure.primaryadapters.adapter.response;

import com.agrosync.crosscutting.helpers.ObjectHelper;

import java.util.ArrayList;
import java.util.List;

public class Response {

    private List<String> mensajes = new ArrayList<>();

    public List<String> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<String> mensajes) {
        this.mensajes = ObjectHelper.getDefault(mensajes, this.mensajes);
    }
}
