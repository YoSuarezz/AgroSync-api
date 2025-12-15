package com.agrosync.application.primaryports.dto.carteras.request;

import com.agrosync.application.primaryports.dto.cuentaspagar.request.CuentaPagarIdSuscripcionDTO;
import com.agrosync.crosscutting.helpers.UUIDHelper;

import java.util.UUID;

public class CarteraIdSuscripcionDTO {

    private UUID id;
    private UUID suscripcionId;

    public CarteraIdSuscripcionDTO() {
        setId(UUIDHelper.getDefault());
        setSuscripcionId(null);
    }

    public CarteraIdSuscripcionDTO(UUID id, UUID suscripcionId) {
        setId(id);
        setSuscripcionId(suscripcionId);
    }

    public static CarteraIdSuscripcionDTO create(UUID id, UUID suscripcionId) {
        return new CarteraIdSuscripcionDTO(id, suscripcionId);
    }

    public static CarteraIdSuscripcionDTO create() {
        return new CarteraIdSuscripcionDTO();
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
