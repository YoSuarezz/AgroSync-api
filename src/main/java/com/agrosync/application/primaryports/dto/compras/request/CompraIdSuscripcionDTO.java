package com.agrosync.application.primaryports.dto.compras.request;

import com.agrosync.crosscutting.helpers.UUIDHelper;

import java.util.UUID;

public class CompraIdSuscripcionDTO {

    private UUID id;
    private UUID suscripcionId;

    public CompraIdSuscripcionDTO() {
        setId(UUIDHelper.getDefault());
        setSuscripcionId(UUIDHelper.getDefault());
    }

    public CompraIdSuscripcionDTO(UUID id, UUID suscripcionId) {
        setId(id);
        setSuscripcionId(suscripcionId);
    }

    public static CompraIdSuscripcionDTO create(UUID id, UUID suscripcionId) {
        return new CompraIdSuscripcionDTO(id, suscripcionId);
    }

    public static CompraIdSuscripcionDTO create() {
        return new CompraIdSuscripcionDTO();
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
        this.suscripcionId = UUIDHelper.getDefault(suscripcionId, UUIDHelper.getDefault());
    }
}
