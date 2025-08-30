package com.agrosync.application.primaryports.dto.usuarios;

import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.crosscutting.helpers.TextHelper;

public class UsuarioDTO {

    private Long id;
    private String nombre;
    private String telefono;
    private TipoUsuarioDTO tipo_usuario;

    public UsuarioDTO() {
        setId(id);
        setNombre(TextHelper.EMPTY);
        setTelefono(TextHelper.EMPTY);
        setTipo_usuario(TipoUsuarioDTO.create());
    }

    public UsuarioDTO(Long id, String nombre, String telefono, TipoUsuarioDTO tipoUsuario) {
        setId(id);
        setNombre(nombre);
        setTelefono(telefono);
        setTipo_usuario(tipoUsuario);
    }

    public static UsuarioDTO create(Long id, String nombre, String telefono, TipoUsuarioDTO tipoUsuario) {
        return new UsuarioDTO(id, nombre, telefono, tipoUsuario);
    }

    public static UsuarioDTO create() {
        return new UsuarioDTO();
    }

    public Long getId() {
        return id;
    }

    public UsuarioDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getNombre() {
        return nombre;
    }

    public UsuarioDTO setNombre(String nombre) {
        this.nombre = TextHelper.applyTrim(nombre);
        return this;
    }

    public String getTelefono() {
        return TextHelper.applyTrim(telefono);
    }

    public UsuarioDTO setTelefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public TipoUsuarioDTO getTipo_usuario() {
        return tipo_usuario;
    }

    public UsuarioDTO setTipo_usuario(TipoUsuarioDTO tipo_usuario) {
        this.tipo_usuario = ObjectHelper.getDefault(tipo_usuario, TipoUsuarioDTO.create());
        return this;
    }
}
