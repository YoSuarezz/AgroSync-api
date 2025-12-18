package com.agrosync.application.primaryports.dto.usuarios.request;

import com.agrosync.application.primaryports.dto.usuarios.response.ObtenerUsuarioDTO;
import com.agrosync.domain.enums.usuarios.TipoUsuarioEnum;
import com.agrosync.crosscutting.helpers.ObjectHelper;

import java.util.UUID;

public class UsuarioPageDTO {

    private int page;
    private int size;
    private String sortBy;
    private String sortDirection;
    private ObtenerUsuarioDTO usuario;
    private TipoUsuarioEnum tipoUsuario;
    private UUID suscripcionId;

    public UsuarioPageDTO() {
    }

    public UsuarioPageDTO(int page, int size, String sortBy, String sortDirection, ObtenerUsuarioDTO usuario, TipoUsuarioEnum tipoUsuario, UUID suscripcionId) {
        setPage(page);
        setSize(size);
        setSortBy(sortBy);
        setSortDirection(sortDirection);
        setUsuario(usuario);
        setTipoUsuario(tipoUsuario);
        setSuscripcionId(suscripcionId);
    }

    public static UsuarioPageDTO create(int page, int size, String sortBy, String sortDirection, ObtenerUsuarioDTO usuario, TipoUsuarioEnum tipoUsuario, UUID suscripcionId) {
        return new UsuarioPageDTO(page, size, sortBy, sortDirection, usuario, tipoUsuario, suscripcionId);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = Math.max(page, 0);
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = Math.max(size, 1);
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }

    public ObtenerUsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(ObtenerUsuarioDTO usuario) {
        this.usuario = ObjectHelper.getDefault(usuario, ObtenerUsuarioDTO.create());
    }

    public TipoUsuarioEnum getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuarioEnum tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public UUID getSuscripcionId() {
        return suscripcionId;
    }

    public void setSuscripcionId(UUID suscripcionId) {
        this.suscripcionId = suscripcionId;
    }
}
