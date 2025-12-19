package com.agrosync.application.primaryports.dto.cuentascobrar.request;

import com.agrosync.application.primaryports.dto.cuentascobrar.response.ObtenerCuentaCobrarDTO;
import com.agrosync.domain.enums.cuentas.EstadoCuentaEnum;

import java.util.UUID;

public class CuentaCobrarPageDTO {

    private int page;
    private int size;
    private String sortBy;
    private String sortDirection;
    private ObtenerCuentaCobrarDTO obtenerCuentaCobrar;
    private EstadoCuentaEnum estado;
    private UUID suscripcionId;
    private Boolean soloConSaldoPendiente;

    public CuentaCobrarPageDTO() {
    }

    public CuentaCobrarPageDTO(int page, int size, String sortBy, String sortDirection,
            ObtenerCuentaCobrarDTO obtenerCuentaCobrar, EstadoCuentaEnum estado, UUID suscripcionId) {
        setPage(page);
        setSize(size);
        setSortBy(sortBy);
        setSortDirection(sortDirection);
        setCuentaCobrar(obtenerCuentaCobrar);
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

    public EstadoCuentaEnum getEstado() {
        return estado;
    }

    public void setEstado(EstadoCuentaEnum estado) {
        this.estado = estado;
    }

    public ObtenerCuentaCobrarDTO getCuentaCobrar() {
        return obtenerCuentaCobrar;
    }

    public void setCuentaCobrar(ObtenerCuentaCobrarDTO obtenerCuentaCobrar) {
        this.obtenerCuentaCobrar = obtenerCuentaCobrar;
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

    public Boolean getSoloConSaldoPendiente() {
        return soloConSaldoPendiente;
    }

    public void setSoloConSaldoPendiente(Boolean soloConSaldoPendiente) {
        this.soloConSaldoPendiente = soloConSaldoPendiente;
    }
}