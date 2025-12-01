package com.agrosync.domain.usuarios;

import com.agrosync.domain.BaseDomain;

import java.util.UUID;

public class TipoUsuarioDomain extends BaseDomain {

    private String nombre;

    public TipoUsuarioDomain() {
        super();
    }

    public TipoUsuarioDomain(UUID id, String nombre) {
        super(id);
        setNombre(nombre);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
