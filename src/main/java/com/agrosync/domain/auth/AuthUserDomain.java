package com.agrosync.domain.auth;

import com.agrosync.application.primaryports.enums.auth.RolEnum;
import com.agrosync.domain.BaseDomain;

import java.util.UUID;

public class AuthUserDomain extends BaseDomain {

    private String email;
    private String password;
    private RolEnum rol;

    public AuthUserDomain() {
        super();
    }

    public AuthUserDomain(UUID id, String email, String password, RolEnum rol) {
        super(id);
        this.email = email;
        this.password = password;
        this.rol = rol;
    }

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
