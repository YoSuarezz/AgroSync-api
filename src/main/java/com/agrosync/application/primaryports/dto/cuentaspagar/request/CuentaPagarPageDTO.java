package com.agrosync.application.primaryports.dto.cuentaspagar.request;

import com.agrosync.application.primaryports.dto.cuentaspagar.response.ObtenerCuentaPagarDTO;
import com.agrosync.application.primaryports.enums.cuentas.EstadoCuentaEnum;

import java.util.UUID;

public class CuentaPagarPageDTO {

    private int page;
    private int size;
    private String sortBy;
    private String sortDirection;
    private ObtenerCuentaPagarDTO obtenerCuentaPagar;
    private EstadoCuentaEnum estado;
    private UUID suscripcionId;

    public CuentaPagarPageDTO() {
    }

    public CuentaPagarPageDTO(int page, int size, String sortBy, String sortDirection, ObtenerCuentaPagarDTO obtenerCuentaPagar, EstadoCuentaEnum estado, UUID suscripcionId) {
        setPage(page);
        setSize(size);
        setSortBy(sortBy);
        setSortDirection(sortDirection);
        setCuentaPagar(obtenerCuentaPagar);
        setEstado(estado);
        setSuscripcionId(suscripcionId);
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

    public ObtenerCuentaPagarDTO getCuentaPagar() {
        return obtenerCuentaPagar;
    }

    public void setCuentaPagar(ObtenerCuentaPagarDTO obtenerCuentaPagar) {
        this.obtenerCuentaPagar = obtenerCuentaPagar;
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

    public EstadoCuentaEnum getEstado() {
        return estado;
    }

    public void setEstado(EstadoCuentaEnum estado) {
        this.estado = estado;
    }
}
