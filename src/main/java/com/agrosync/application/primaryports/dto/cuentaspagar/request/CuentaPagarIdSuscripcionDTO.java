package com.agrosync.application.primaryports.dto.cuentaspagar.request;

import com.agrosync.crosscutting.helpers.UUIDHelper;

import java.util.UUID;

public class CuentaPagarIdSuscripcionDTO {

    private UUID id;
    private UUID suscripcionId;

    public CuentaPagarIdSuscripcionDTO() {
        setId(UUIDHelper.getDefault());
        setSuscripcionId(null);
    }

    public CuentaPagarIdSuscripcionDTO(UUID id, UUID suscripcionId) {
        setId(id);
        setSuscripcionId(suscripcionId);
    }

    public static CuentaPagarIdSuscripcionDTO create(UUID id, UUID suscripcionId) {
        return new CuentaPagarIdSuscripcionDTO(id, suscripcionId);
    }

    public static CuentaPagarIdSuscripcionDTO create() {
        return new CuentaPagarIdSuscripcionDTO();
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
