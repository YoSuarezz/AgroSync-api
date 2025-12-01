package com.agrosync.application.primaryports.dto.usuarios.request;

import com.agrosync.application.primaryports.enums.usuarios.TipoUsuarioEnum;
import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.crosscutting.helpers.TextHelper;

public class RegistrarNuevoUsuarioDTO {

    private String nombre;
    private String telefono;
    private TipoUsuarioEnum tipoUsuario;

    public RegistrarNuevoUsuarioDTO() {
        setNombre(TextHelper.EMPTY);
        setTelefono(TextHelper.EMPTY);
        setTipoUsuario(TipoUsuarioEnum.CLIENTE);
    }

    public RegistrarNuevoUsuarioDTO(String nombre, String telefono, TipoUsuarioEnum tipoUsuario) {
        setNombre(nombre);
        setTelefono(telefono);
        setTipoUsuario(tipoUsuario);
    }

    public static RegistrarNuevoUsuarioDTO create(String nombre, String telefono, TipoUsuarioEnum tipoUsuario) {
        return new RegistrarNuevoUsuarioDTO(nombre, telefono, tipoUsuario);
    }

    public static RegistrarNuevoUsuarioDTO create() {
        return new RegistrarNuevoUsuarioDTO();
    }

    public String getNombre() {
        return nombre;
    }

    public RegistrarNuevoUsuarioDTO setNombre(String nombre) {
        this.nombre = TextHelper.applyTrim(nombre);
        return this;
    }

    public String getTelefono() {
        return TextHelper.applyTrim(telefono);
    }

    public RegistrarNuevoUsuarioDTO setTelefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public TipoUsuarioEnum getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuarioEnum tipoUsuario) {
        this.tipoUsuario = ObjectHelper.getDefault(tipoUsuario, TipoUsuarioEnum.CLIENTE);
    }
}
