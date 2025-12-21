package com.agrosync.application.primaryports.dto.abonos.request;

import com.agrosync.crosscutting.helpers.UUIDHelper;

import java.util.UUID;

public class AbonoIdSuscripcionDTO {

    private UUID id;
    private UUID suscripcionId;

    public AbonoIdSuscripcionDTO() {
        setId(UUIDHelper.getDefault());
        setSuscripcionId(null);
    }

    public AbonoIdSuscripcionDTO(UUID id, UUID suscripcionId) {
        setId(id);
        setSuscripcionId(suscripcionId);
    }

    public static AbonoIdSuscripcionDTO create(UUID id, UUID suscripcionId) {
        return new AbonoIdSuscripcionDTO(id, suscripcionId);
    }

    public static AbonoIdSuscripcionDTO create() {
        return new AbonoIdSuscripcionDTO();
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
