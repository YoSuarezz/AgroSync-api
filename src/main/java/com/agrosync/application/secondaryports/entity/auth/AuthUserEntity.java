package com.agrosync.application.secondaryports.entity.auth;

import com.agrosync.domain.enums.auth.RolEnum;
import com.agrosync.application.secondaryports.entity.suscripcion.SuscripcionEntity;
import com.agrosync.crosscutting.helpers.UUIDHelper;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "auth_user", schema = "agryxo")
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

    @Column(name = "activo", nullable = false)
    private boolean activo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_suscripcion")
    private SuscripcionEntity suscripcion;

    public AuthUserEntity() {
        this.id = UUIDHelper.getDefault();
        this.suscripcion = null;
        this.activo = true;
    }

    public AuthUserEntity(UUID id, String email, String password, RolEnum rol, SuscripcionEntity suscripcion) {
        this.id = UUIDHelper.getDefault(id, UUIDHelper.getDefault());
        this.email = email;
        this.password = password;
        this.rol = rol;
        this.suscripcion = suscripcion;
        this.activo = true;
    }

    public static AuthUserEntity create(UUID id, String email, String password, RolEnum rol) {
        return new AuthUserEntity(id, email, password, rol, null);
    }

    public static AuthUserEntity create() {
        return new AuthUserEntity(UUIDHelper.getDefault(), null, null, RolEnum.EMPLEADO, null);
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

    public SuscripcionEntity getSuscripcion() {
        return suscripcion;
    }

    public AuthUserEntity setSuscripcion(SuscripcionEntity suscripcion) {
        this.suscripcion = suscripcion;
        return this;
    }

    public boolean isActivo() {
        return activo;
    }

    public AuthUserEntity setActivo(boolean activo) {
        this.activo = activo;
        return this;
    }
}
