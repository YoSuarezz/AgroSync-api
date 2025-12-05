package com.agrosync.application.primaryports.dto.usuarios.request;

import com.agrosync.application.primaryports.enums.usuarios.TipoUsuarioEnum;
import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.crosscutting.helpers.TextHelper;
import com.agrosync.crosscutting.helpers.UUIDHelper;

import java.util.UUID;

public class ActualizarUsuarioDTO {

    private UUID id;
    private String nombre;
    private String telefono;
    private TipoUsuarioEnum tipoUsuario;
    private UUID suscripcionId;

    public ActualizarUsuarioDTO() {
        setId(UUIDHelper.getDefault());
        setNombre(TextHelper.EMPTY);
        setTelefono(TextHelper.EMPTY);
        setTipoUsuario(TipoUsuarioEnum.CLIENTE);
        setSuscripcionId(null);
    }

    public ActualizarUsuarioDTO(UUID id, String nombre, String telefono, TipoUsuarioEnum tipoUsuario) {
        setId(id);
        setNombre(nombre);
        setTelefono(telefono);
        setTipoUsuario(tipoUsuario);
    }

    public static ActualizarUsuarioDTO create(UUID id, String nombre, String telefono, TipoUsuarioEnum tipoUsuario) {
        return new ActualizarUsuarioDTO(id, nombre, telefono, tipoUsuario);
    }

    public static ActualizarUsuarioDTO create() {
        return new ActualizarUsuarioDTO();
    }

    public UUID getId() {
        return id;
    }

    public ActualizarUsuarioDTO setId(UUID id) {
        this.id = UUIDHelper.getDefault(id, UUIDHelper.getDefault());
        return this;
    }

    public String getNombre() {
        return nombre;
    }

    public ActualizarUsuarioDTO setNombre(String nombre) {
        this.nombre = TextHelper.applyTrim(nombre);
        return this;
    }

    public String getTelefono() {
        return TextHelper.applyTrim(telefono);
    }

    public ActualizarUsuarioDTO setTelefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public TipoUsuarioEnum getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuarioEnum tipoUsuario) {
        this.tipoUsuario = ObjectHelper.getDefault(tipoUsuario, TipoUsuarioEnum.CLIENTE);
    }

    public UUID getSuscripcionId() {
        return suscripcionId;
    }

    public void setSuscripcionId(UUID suscripcionId) {
        this.suscripcionId = suscripcionId;
    }
}
