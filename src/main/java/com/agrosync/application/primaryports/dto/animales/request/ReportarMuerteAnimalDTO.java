package com.agrosync.application.primaryports.dto.animales.request;

import com.agrosync.crosscutting.helpers.UUIDHelper;

import java.util.UUID;

public class ReportarMuerteAnimalDTO {

    private UUID id;
    private UUID suscripcionId;

    public ReportarMuerteAnimalDTO() {
        setId(UUIDHelper.getDefault());
        setSuscripcionId(UUIDHelper.getDefault());
    }

    public ReportarMuerteAnimalDTO(UUID id, UUID suscripcionId) {
        setId(id);
        setSuscripcionId(suscripcionId);
    }

    public static ReportarMuerteAnimalDTO create(UUID id, UUID suscripcionId) {
        return new ReportarMuerteAnimalDTO(id, suscripcionId);
    }

    public static ReportarMuerteAnimalDTO create() {
        return new ReportarMuerteAnimalDTO();
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
