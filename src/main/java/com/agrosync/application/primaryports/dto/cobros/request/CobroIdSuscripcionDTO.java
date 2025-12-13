package com.agrosync.application.primaryports.dto.cobros.request;

import com.agrosync.crosscutting.helpers.UUIDHelper;

import java.util.UUID;

public class CobroIdSuscripcionDTO {

    private UUID id;
    private UUID suscripcionId;

    public CobroIdSuscripcionDTO() {
        setId(UUIDHelper.getDefault());
        setSuscripcionId(null);
    }

    public CobroIdSuscripcionDTO(UUID id, UUID suscripcionId) {
        setId(id);
        setSuscripcionId(suscripcionId);
    }

    public static CobroIdSuscripcionDTO create(UUID id, UUID suscripcionId) {
        return new CobroIdSuscripcionDTO(id, suscripcionId);
    }

    public static CobroIdSuscripcionDTO create() {
        return new CobroIdSuscripcionDTO();
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
