package com.agrosync.infrastructure.primaryadapters.adapter.response.auth;

import com.agrosync.domain.enums.suscripcion.EstadoSuscripcionEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AuthUserInfoResponse {

    private List<String> mensajes;
    private UUID userId;
    private String email;
    private String rol;
    private UUID suscripcionId;
    private EstadoSuscripcionEnum estadoSuscripcionEnum;

    public AuthUserInfoResponse() {
        this.mensajes = new ArrayList<>();
    }

    public UUID getUserId() {
        return userId;
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

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getSuscripcionId() {
        return suscripcionId;
    }

    public void setSuscripcionId(UUID suscripcionId) {
        this.suscripcionId = suscripcionId;
    }

    public EstadoSuscripcionEnum getEstadoSuscripcionEnum() {
        return estadoSuscripcionEnum;
    }

    public void setEstadoSuscripcionEnum(EstadoSuscripcionEnum estadoSuscripcionEnum) {
        this.estadoSuscripcionEnum = estadoSuscripcionEnum;
    }
}
