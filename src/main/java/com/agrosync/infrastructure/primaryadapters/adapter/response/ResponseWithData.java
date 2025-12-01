package com.agrosync.infrastructure.primaryadapters.adapter.response;

import com.agrosync.crosscutting.helpers.ObjectHelper;

public class ResponseWithData<T> extends Response {

    private T datos;

    public T getDatos() {
        return datos;
    }

    public void setDatos(T datos) {
        this.datos = ObjectHelper.getDefault(datos, this.datos);
    }
}
