package com.agrosync.application.primaryports.dto.cuentascobrar.request;

import com.agrosync.crosscutting.helpers.UUIDHelper;

import java.util.UUID;

public class CuentaCobrarIdSuscripcionDTO {

    private UUID id;
    private UUID suscripcionId;

    public CuentaCobrarIdSuscripcionDTO() {
        setId(UUIDHelper.getDefault());
        setSuscripcionId(null);
    }

    public CuentaCobrarIdSuscripcionDTO(UUID id, UUID suscripcionId) {
        setId(id);
        setSuscripcionId(suscripcionId);
    }

    public static CuentaCobrarIdSuscripcionDTO create(UUID id, UUID suscripcionId) {
        return new CuentaCobrarIdSuscripcionDTO(id, suscripcionId);
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