package com.agrosync.domain.usuarios;

import java.util.UUID;

public class TipoUsuarioDomain {

    private UUID id;
    private String nombre;

    public TipoUsuarioDomain(UUID id, String nombre) {
        setId(id);
        setNombre(nombre);
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

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
