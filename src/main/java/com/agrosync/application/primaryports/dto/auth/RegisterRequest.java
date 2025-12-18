package com.agrosync.application.primaryports.dto.auth;

import com.agrosync.domain.enums.auth.RolEnum;

public class RegisterRequest {

    private String email;
    private String password;
    private RolEnum rol;
    private java.util.UUID suscripcionId;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RolEnum getRol() {
        return rol;
    }

    public void setRol(RolEnum rol) {
        this.rol = rol;
    }

    public java.util.UUID getSuscripcionId() {
        return suscripcionId;
    }

    public void setSuscripcionId(java.util.UUID suscripcionId) {
        this.suscripcionId = suscripcionId;
    }
}
