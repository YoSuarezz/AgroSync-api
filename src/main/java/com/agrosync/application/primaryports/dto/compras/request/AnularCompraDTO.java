package com.agrosync.application.primaryports.dto.compras.request;

import java.util.UUID;

public class AnularCompraDTO {

    private UUID compraId;
    private String motivo;
    private UUID suscripcionId;

    public AnularCompraDTO() {
    }

    public AnularCompraDTO(UUID compraId, String motivo, UUID suscripcionId) {
        this.compraId = compraId;
        this.motivo = motivo;
        this.suscripcionId = suscripcionId;
    }

    public static AnularCompraDTO create(UUID compraId, String motivo, UUID suscripcionId) {
        return new AnularCompraDTO(compraId, motivo, suscripcionId);
    }

    public UUID getCompraId() {
        return compraId;
    }

    public void setCompraId(UUID compraId) {
        this.compraId = compraId;
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

