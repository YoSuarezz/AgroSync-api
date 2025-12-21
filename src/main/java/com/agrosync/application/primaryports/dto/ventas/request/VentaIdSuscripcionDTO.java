package com.agrosync.application.primaryports.dto.ventas.request;

import com.agrosync.crosscutting.helpers.UUIDHelper;

import java.util.UUID;

public class VentaIdSuscripcionDTO {

    private UUID id;
    private UUID suscripcionId;

    public VentaIdSuscripcionDTO() {
        setId(UUIDHelper.getDefault());
        setSuscripcionId(null);
    }

    public VentaIdSuscripcionDTO(UUID id, UUID suscripcionId) {
        setId(id);
        setSuscripcionId(suscripcionId);
    }

    public static VentaIdSuscripcionDTO create(UUID id, UUID suscripcionId) {
        return new VentaIdSuscripcionDTO(id, suscripcionId);
    }

    public static VentaIdSuscripcionDTO create() {
        return new VentaIdSuscripcionDTO();
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
