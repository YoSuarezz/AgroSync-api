package com.agrosync.application.primaryports.dto.auth;

public class ActualizarEstadoUsuarioRequest {

    private boolean activo;

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
