package com.agrosync.infrastructure.primaryadapters.adapter.response.auth;

import java.util.ArrayList;
import java.util.List;

public class AuthResponse {

    private String token;
    private String rol;
    private List<String> mensajes;

    public AuthResponse() {
        this.mensajes = new ArrayList<>();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public List<String> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<String> mensajes) {
        this.mensajes = mensajes;
    }
}
