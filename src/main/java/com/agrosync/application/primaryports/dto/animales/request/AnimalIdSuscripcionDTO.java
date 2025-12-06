package com.agrosync.application.primaryports.dto.animales.request;

import com.agrosync.crosscutting.helpers.UUIDHelper;

import java.util.UUID;

public class AnimalIdSuscripcionDTO {

    private UUID id;
    private UUID suscripcionId;

    public AnimalIdSuscripcionDTO() {
        setId(UUIDHelper.getDefault());
        setSuscripcionId(null);
    }

    public AnimalIdSuscripcionDTO(UUID id, UUID suscripcionId) {
        setId(id);
        setSuscripcionId(suscripcionId);
    }

    public static AnimalIdSuscripcionDTO create(UUID id, UUID suscripcionId) {
        return new AnimalIdSuscripcionDTO(id, suscripcionId);
    }

    public static AnimalIdSuscripcionDTO create() {
        return new AnimalIdSuscripcionDTO();
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
