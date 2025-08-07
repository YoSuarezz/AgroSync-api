package com.agrosync.domain.usuarios;

public class TipoUsuarioDomain {

    private Long id;
    private String nombre;

    public TipoUsuarioDomain(Long id, String nombre) {
        setId(id);
        setNombre(nombre);
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
        this.nombre = nombre;
    }
}
