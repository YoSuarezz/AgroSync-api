package com.sedikev.application.primaryports.dto.usuarios;

import com.sedikev.crosscutting.helpers.TextHelper;

public class TipoUsuarioDTO {

    private Long id;
    private String nombre;

    public TipoUsuarioDTO() {
        setId(id);
        setNombre(TextHelper.EMPTY);
    }

    public TipoUsuarioDTO(Long id, String nombre) {
        setId(id);
        setNombre(nombre);
    }

    public static TipoUsuarioDTO create(Long id, String nombre) {
        return new TipoUsuarioDTO(id, nombre);
    }

    public static TipoUsuarioDTO create() {
        return new TipoUsuarioDTO();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = TextHelper.applyTrim(nombre);
    }
}
