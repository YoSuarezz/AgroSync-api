package com.agrosync.application.primaryports.dto.auth;

import com.agrosync.domain.enums.auth.RolEnum;
import com.agrosync.crosscutting.helpers.TextHelper;
import com.agrosync.crosscutting.helpers.UUIDHelper;

import java.util.UUID;

public class AuthUserDTO {

    private UUID id;
    private String email;
    private RolEnum rol;
    private boolean activo;

    public AuthUserDTO(UUID id, String email, RolEnum rol) {
        setId(id);
        setEmail(email);
        setRol(rol);
        setActivo(true);
    }

    public AuthUserDTO() {
        setId(UUIDHelper.getDefault());
        setEmail(TextHelper.EMPTY);
        setRol(RolEnum.EMPLEADO);
        setActivo(true);
    }

    public static AuthUserDTO create(UUID id, String email, RolEnum rol) {
        return new AuthUserDTO(id, email, rol);
    }

    public static AuthUserDTO create() {
        return new AuthUserDTO();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = UUIDHelper.getDefault(id, UUIDHelper.getDefault());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = TextHelper.applyTrim(email);
    }

    public RolEnum getRol() {
        return rol;
    }

    public void setRol(RolEnum rol) {
        this.rol = rol;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
