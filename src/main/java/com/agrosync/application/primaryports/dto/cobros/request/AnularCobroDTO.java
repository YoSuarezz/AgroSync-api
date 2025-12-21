package com.agrosync.application.primaryports.dto.cobros.request;

import java.util.UUID;

public class AnularCobroDTO {

    private UUID cobroId;
    private UUID cuentaCobrarId;
    private String motivo;
    private UUID suscripcionId;

    public AnularCobroDTO() {
    }

    public AnularCobroDTO(UUID cobroId, UUID cuentaCobrarId, String motivo, UUID suscripcionId) {
        this.cobroId = cobroId;
        this.cuentaCobrarId = cuentaCobrarId;
        this.motivo = motivo;
        this.suscripcionId = suscripcionId;
    }

    public static AnularCobroDTO create(UUID cobroId, UUID cuentaCobrarId, String motivo, UUID suscripcionId) {
        return new AnularCobroDTO(cobroId, cuentaCobrarId, motivo, suscripcionId);
    }

    public UUID getCobroId() {
        return cobroId;
    }

    public void setCobroId(UUID cobroId) {
        this.cobroId = cobroId;
    }

    public UUID getCuentaCobrarId() {
        return cuentaCobrarId;
    }

    public void setCuentaCobrarId(UUID cuentaCobrarId) {
        this.cuentaCobrarId = cuentaCobrarId;
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

