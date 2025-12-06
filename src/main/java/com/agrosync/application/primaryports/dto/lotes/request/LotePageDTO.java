package com.agrosync.application.primaryports.dto.lotes.request;

import com.agrosync.application.primaryports.dto.lotes.response.ObtenerLoteDTO;
import com.agrosync.crosscutting.helpers.ObjectHelper;

import java.util.UUID;

public class LotePageDTO {

    private int page;
    private int size;
    private String sortBy;
    private String sortDirection;
    private ObtenerLoteDTO obtenerLote;
    private UUID suscripcionId;

    public LotePageDTO() {
    }

    public LotePageDTO(int page, int size, String sortBy, String sortDirection, ObtenerLoteDTO obtenerLote, UUID suscripcionId) {
        setPage(page);
        setSize(size);
        setSortBy(sortBy);
        setSortDirection(sortDirection);
        setLote(obtenerLote);
        setSuscripcionId(suscripcionId);
    }

    public static LotePageDTO create(int page, int size, String sortBy, String sortDirection, ObtenerLoteDTO obtenerLote, UUID suscripcionId) {
        return new LotePageDTO(page, size, sortBy, sortDirection, obtenerLote, suscripcionId);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = Math.max(page, 0);
    }

    public UUID getSuscripcionId() {
        return suscripcionId;
    }

    public void setSuscripcionId(UUID suscripcionId) {
        this.suscripcionId = suscripcionId;
    }

    public ObtenerLoteDTO getLote() {
        return obtenerLote;
    }

    public void setLote(ObtenerLoteDTO obtenerLote) {
        this.obtenerLote = ObjectHelper.getDefault(obtenerLote, ObtenerLoteDTO.create());
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = Math.max(size, 1);
    }
}
