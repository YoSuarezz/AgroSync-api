package com.agrosync.application.primaryports.dto.usuarios.request;

import com.agrosync.crosscutting.helpers.UUIDHelper;

import java.util.UUID;

public class UsuarioIdSuscripcionDTO {

    private UUID id;
    private UUID suscripcionId;

    public UsuarioIdSuscripcionDTO() {
        setId(UUIDHelper.getDefault());
        setSuscripcionId(null);
    }

    public UsuarioIdSuscripcionDTO(UUID id, UUID suscripcionId) {
        setId(id);
        setSuscripcionId(suscripcionId);
    }

    public static UsuarioIdSuscripcionDTO create(UUID id, UUID suscripcionId) {
        return new UsuarioIdSuscripcionDTO(id, suscripcionId);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = UUIDHelper.getDefault(id, UUIDHelper.getDefault());
    }

    public UUID getSuscripcionId() {
        return suscripcionId;
    }

    public void setSuscripcionId(UUID suscripcionId) {
        this.suscripcionId = suscripcionId;
    }
}
