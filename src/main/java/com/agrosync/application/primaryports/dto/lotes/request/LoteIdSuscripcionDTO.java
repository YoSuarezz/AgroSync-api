package com.agrosync.application.primaryports.dto.lotes.request;

import com.agrosync.crosscutting.helpers.UUIDHelper;

import java.util.UUID;

public class LoteIdSuscripcionDTO {

    private UUID id;
    private UUID suscripcionId;

    public LoteIdSuscripcionDTO() {
        setId(UUID.randomUUID());
        setSuscripcionId(null);
    }

    public LoteIdSuscripcionDTO(UUID id, UUID suscripcionId) {
        setId(id);
        setSuscripcionId(suscripcionId);
    }

    public static LoteIdSuscripcionDTO create(UUID id, UUID suscripcionId) {
        return new LoteIdSuscripcionDTO(id, suscripcionId);
    }

    public static LoteIdSuscripcionDTO create() {
        return new LoteIdSuscripcionDTO();
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
