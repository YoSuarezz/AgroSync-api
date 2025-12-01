package com.agrosync.application.primaryports.dto.auth;

import com.agrosync.application.primaryports.enums.auth.RolEnum;

public class RegisterRequest {

    private String email;
    private String password;
    private RolEnum rol;

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
}
