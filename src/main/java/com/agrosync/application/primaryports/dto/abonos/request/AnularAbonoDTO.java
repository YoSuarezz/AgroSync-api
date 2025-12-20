package com.agrosync.application.primaryports.dto.abonos.request;

import java.util.UUID;

public class AnularAbonoDTO {

    private UUID abonoId;
    private UUID cuentaPagarId;
    private String motivo;
    private UUID suscripcionId;

    public AnularAbonoDTO() {
    }

    public AnularAbonoDTO(UUID abonoId, UUID cuentaPagarId, String motivo, UUID suscripcionId) {
        this.abonoId = abonoId;
        this.cuentaPagarId = cuentaPagarId;
        this.motivo = motivo;
        this.suscripcionId = suscripcionId;
    }

    public static AnularAbonoDTO create(UUID abonoId, UUID cuentaPagarId, String motivo, UUID suscripcionId) {
        return new AnularAbonoDTO(abonoId, cuentaPagarId, motivo, suscripcionId);
    }

    public UUID getAbonoId() {
        return abonoId;
    }

    public void setAbonoId(UUID abonoId) {
        this.abonoId = abonoId;
    }

    public UUID getCuentaPagarId() {
        return cuentaPagarId;
    }

    public void setCuentaPagarId(UUID cuentaPagarId) {
        this.cuentaPagarId = cuentaPagarId;
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

