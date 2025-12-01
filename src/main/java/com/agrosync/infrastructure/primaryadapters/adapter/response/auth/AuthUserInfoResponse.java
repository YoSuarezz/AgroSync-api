package com.agrosync.infrastructure.primaryadapters.adapter.response.auth;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AuthUserInfoResponse {

    private UUID id;
    private String email;
    private String rol;
    private List<String> mensajes;

    public AuthUserInfoResponse() {
        this.mensajes = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
