package com.agrosync.application.primaryports.dto.suscripcion.request;

import java.util.UUID;

public class SuscripcionDTO {

    private UUID id;
    private String email;

    public SuscripcionDTO(UUID id, String email) {
        this.id = id;
        this.email = email;
    }

    public SuscripcionDTO() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
