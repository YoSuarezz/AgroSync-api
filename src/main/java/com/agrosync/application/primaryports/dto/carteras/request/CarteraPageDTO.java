package com.agrosync.application.primaryports.dto.carteras.request;

import com.agrosync.application.primaryports.dto.carteras.response.ObtenerCarteraDTO;
import com.agrosync.application.primaryports.dto.usuarios.response.ObtenerUsuarioDTO;

import java.time.LocalDate;
import java.util.UUID;

public class CarteraPageDTO {

    private int page;
    private int size;
    private String sortBy;
    private String sortDirection;
    private ObtenerUsuarioDTO usuario;
    private UUID suscripcionId;

    public CarteraPageDTO() {
    }

    public CarteraPageDTO(int page, int size, String sortBy, String sortDirection,
                          ObtenerUsuarioDTO usuario, UUID suscripcionId) {
        setPage(page);
        setSize(size);
        setSortBy(sortBy);
        setSortDirection(sortDirection);
        setUsuario(usuario);
        setSuscripcionId(suscripcionId);
    }

    public static CarteraPageDTO create(int page, int size, String sortBy, String sortDirection,
                                        ObtenerUsuarioDTO usuario, UUID suscripcionId) {
        return new CarteraPageDTO(page, size, sortBy, sortDirection, usuario, suscripcionId);
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
}
