package com.agrosync.application.primaryports.dto.usuarios.request;

import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.crosscutting.helpers.TextHelper;
import com.agrosync.crosscutting.helpers.UUIDHelper;

import java.util.UUID;

public class RegiserNewUserDTO {

    private UUID id;
    private String nombre;
    private String telefono;
    private TipoUsuarioDTO tipoUsuario;

    public RegiserNewUserDTO() {
        setId(UUIDHelper.getDefault());
        setNombre(TextHelper.EMPTY);
        setTelefono(TextHelper.EMPTY);
        setTipoUsuario(TipoUsuarioDTO.create());
    }

    public RegiserNewUserDTO(UUID id, String nombre, String telefono, TipoUsuarioDTO tipoUsuario) {
        setId(id);
        setNombre(nombre);
        setTelefono(telefono);
        setTipoUsuario(tipoUsuario);
    }

    public static RegiserNewUserDTO create(UUID id, String nombre, String telefono, TipoUsuarioDTO tipoUsuario) {
        return new RegiserNewUserDTO(id, nombre, telefono, tipoUsuario);
    }

    public static RegiserNewUserDTO create() {
        return new RegiserNewUserDTO();
    }

    public UUID getId() {
        return id;
    }

    public RegiserNewUserDTO setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getNombre() {
        return nombre;
    }

    public RegiserNewUserDTO setNombre(String nombre) {
        this.nombre = TextHelper.applyTrim(nombre);
        return this;
    }

    public String getTelefono() {
        return TextHelper.applyTrim(telefono);
    }

    public RegiserNewUserDTO setTelefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public TipoUsuarioDTO getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuarioDTO tipoUsuario) {
        this.tipoUsuario = ObjectHelper.getDefault(tipoUsuario, TipoUsuarioDTO.create());
    }
}
