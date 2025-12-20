package com.agrosync.application.primaryports.dto.carteras.request;

import com.agrosync.application.primaryports.dto.usuarios.response.ObtenerUsuarioDTO;
import com.agrosync.domain.enums.usuarios.TipoUsuarioEnum;

import java.util.UUID;

public class CarteraPageDTO {

    private int page;
    private int size;
    private String sortBy;
    private String sortDirection;
    private ObtenerUsuarioDTO usuario;
    private UUID suscripcionId;
    private TipoUsuarioEnum tipoUsuario;

    public CarteraPageDTO() {
    }

    public CarteraPageDTO(int page, int size, String sortBy, String sortDirection,
                          ObtenerUsuarioDTO usuario, UUID suscripcionId, TipoUsuarioEnum tipoUsuario) {
        setPage(page);
        setSize(size);
        setSortBy(sortBy);
        setSortDirection(sortDirection);
        setUsuario(usuario);
        setSuscripcionId(suscripcionId);
        setTipoUsuario(tipoUsuario);
    }

    public static CarteraPageDTO create(int page, int size, String sortBy, String sortDirection,
                                        ObtenerUsuarioDTO usuario, UUID suscripcionId, TipoUsuarioEnum tipoUsuario) {
        return new CarteraPageDTO(page, size, sortBy, sortDirection, usuario, suscripcionId, tipoUsuario);
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
        this.usuario = usuario;
    }

    public UUID getSuscripcionId() {
        return suscripcionId;
    }

    public void setSuscripcionId(UUID suscripcionId) {
        this.suscripcionId = suscripcionId;
    }

    public TipoUsuarioEnum getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuarioEnum tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
