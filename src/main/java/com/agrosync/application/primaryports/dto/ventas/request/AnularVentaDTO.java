package com.agrosync.application.primaryports.dto.ventas.request;

import java.util.UUID;

public class AnularVentaDTO {

    private UUID ventaId;
    private String motivo;
    private UUID suscripcionId;

    public AnularVentaDTO() {
    }

    public AnularVentaDTO(UUID ventaId, String motivo, UUID suscripcionId) {
        this.ventaId = ventaId;
        this.motivo = motivo;
        this.suscripcionId = suscripcionId;
    }

    public static AnularVentaDTO create(UUID ventaId, String motivo, UUID suscripcionId) {
        return new AnularVentaDTO(ventaId, motivo, suscripcionId);
    }

    public UUID getVentaId() {
        return ventaId;
    }

    public void setVentaId(UUID ventaId) {
        this.ventaId = ventaId;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public UUID getSuscripcionId() {
        return suscripcionId;
    }

    public void setSuscripcionId(UUID suscripcionId) {
        this.suscripcionId = suscripcionId;
    }
}

