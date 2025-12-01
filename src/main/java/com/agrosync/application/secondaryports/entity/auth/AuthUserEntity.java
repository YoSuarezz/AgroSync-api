package com.agrosync.application.secondaryports.entity.auth;

import com.agrosync.application.primaryports.enums.RolEnum;
import com.agrosync.crosscutting.helpers.UUIDHelper;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "auth_user")
public class AuthUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
    private RolEnum rol;

    public AuthUserEntity() {
        this.id = UUIDHelper.getDefault();
    }

    public AuthUserEntity(UUID id, String email, String password, RolEnum rol) {
        this.id = UUIDHelper.getDefault(id, UUIDHelper.getDefault());
        this.email = email;
        this.password = password;
        this.rol = rol;
    }

    public static AuthUserEntity create(UUID id, String email, String password, RolEnum rol) {
        return new AuthUserEntity(id, email, password, rol);
    }

    public static AuthUserEntity create() {
        return new AuthUserEntity(UUIDHelper.getDefault(), null, null, RolEnum.EMPLEADO);
    }

    public UUID getId() {
        return id;
    }

    public AuthUserEntity setId(UUID id) {
        this.id = UUIDHelper.getDefault(id, UUIDHelper.getDefault());
        return this;
    }

    public String getEmail() {
        return email;
    }

    public AuthUserEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public AuthUserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public RolEnum getRol() {
        return rol;
    }

    public AuthUserEntity setRol(RolEnum rol) {
        this.rol = rol;
        return this;
    }
}
