package com.agrosync.domain.usuarios;

import java.util.UUID;

public class UsuarioDomain {

    private UUID id;
    private String nombre;
    private String telefono;
    private TipoUsuarioDomain tipo_usuario;

    public UsuarioDomain(UUID id, String nombre, String telefono, TipoUsuarioDomain tipo_usuario) {
        setId(id);
        setNombre(nombre);
        setTelefono(telefono);
        setTipo_usuario(tipo_usuario);
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public TipoUsuarioDomain getTipo_usuario() {
        return tipo_usuario;
    }

    public void setTipo_usuario(TipoUsuarioDomain tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }
}
