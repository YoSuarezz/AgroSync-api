package com.agrosync.application.primaryports.dto.usuarios.request;

import com.agrosync.crosscutting.helpers.TextHelper;

import java.util.UUID;

public class TipoUsuarioDTO {

    private UUID id;
    private String nombre;

    public TipoUsuarioDTO() {
        setId(id);
        setNombre(TextHelper.EMPTY);
    }

    public TipoUsuarioDTO(UUID id, String nombre) {
        setId(id);
        setNombre(nombre);
    }

    public static TipoUsuarioDTO create(UUID id, String nombre) {
        return new TipoUsuarioDTO(id, nombre);
    }

    public static TipoUsuarioDTO create() {
        return new TipoUsuarioDTO();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public TipoUsuarioDTO setNombre(String nombre) {
        this.nombre = TextHelper.applyTrim(nombre);
        return this;
    }
}
