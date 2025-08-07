package com.sedikev.domain.usuarios;

public class UsuarioDomain {

    private Long id;
    private String nombre;
    private String telefono;
    private TipoUsuarioDomain tipo_usuario;

    public UsuarioDomain(Long id, String nombre, String telefono, TipoUsuarioDomain tipo_usuario) {
        setId(id);
        setNombre(nombre);
        setTelefono(telefono);
        setTipo_usuario(tipo_usuario);
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
