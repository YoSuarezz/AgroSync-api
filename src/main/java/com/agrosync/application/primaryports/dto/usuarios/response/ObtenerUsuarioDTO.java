package com.agrosync.application.primaryports.dto.usuarios.response;

import com.agrosync.domain.enums.usuarios.TipoUsuarioEnum;
import com.agrosync.crosscutting.helpers.TextHelper;
import com.agrosync.crosscutting.helpers.UUIDHelper;

import java.util.UUID;

public class ObtenerUsuarioDTO {

    private UUID id;
    private String nombre;
    private String telefono;
    private TipoUsuarioEnum tipoUsuario;

    public ObtenerUsuarioDTO() {
        setId(UUIDHelper.getDefault());
        setNombre(TextHelper.EMPTY);
        setTelefono(TextHelper.EMPTY);
        setTipoUsuario(null);
    }

    public ObtenerUsuarioDTO(UUID id, String nombre, String telefono, TipoUsuarioEnum tipoUsuario) {
        setId(id);
        setNombre(nombre);
        setTelefono(telefono);
        setTipoUsuario(tipoUsuario);
    }

    public static ObtenerUsuarioDTO create(UUID id, String nombre, String telefono, TipoUsuarioEnum tipoUsuario) {
        return new ObtenerUsuarioDTO(id, nombre, telefono, tipoUsuario);
    }

    public static ObtenerUsuarioDTO create() {
        return new ObtenerUsuarioDTO();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = UUIDHelper.getDefault(id, UUIDHelper.getDefault());
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = TextHelper.applyTrim(nombre);
    }

    public String getTelefono() {
        return TextHelper.applyTrim(telefono);
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public TipoUsuarioEnum getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuarioEnum tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
