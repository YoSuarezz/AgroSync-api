package com.agrosync.application.primaryports.dto.compras.request;

import java.time.LocalDate;
import java.util.UUID;

public class CompraPageDTO {

    private int page;
    private int size;
    private String sortBy;
    private String sortDirection;
    private UUID suscripcionId;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String numeroCompra;
    private UUID proveedorId;

    public CompraPageDTO() {
    }

    public CompraPageDTO(int page, int size, String sortBy, String sortDirection,
                         UUID suscripcionId, LocalDate fechaInicio, LocalDate fechaFin,
                         String numeroCompra, UUID proveedorId) {
        setPage(page);
        setSize(size);
        setSortBy(sortBy);
        setSortDirection(sortDirection);
        setSuscripcionId(suscripcionId);
        setFechaInicio(fechaInicio);
        setFechaFin(fechaFin);
        setNumeroCompra(numeroCompra);
        setProveedorId(proveedorId);
    }

    public static CompraPageDTO create(int page, int size, String sortBy, String sortDirection, UUID suscripcionId,
                                       LocalDate fechaInicio, LocalDate fechaFin, String numeroCompra, UUID proveedorId) {
        return new CompraPageDTO(page, size, sortBy, sortDirection, suscripcionId, fechaInicio, fechaFin, numeroCompra, proveedorId);
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

    public UUID getSuscripcionId() {
        return suscripcionId;
    }

    public void setSuscripcionId(UUID suscripcionId) {
        this.suscripcionId = suscripcionId;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getNumeroCompra() {
        return numeroCompra;
    }

    public void setNumeroCompra(String numeroCompra) {
        this.numeroCompra = numeroCompra;
    }

    public UUID getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(UUID proveedorId) {
        this.proveedorId = proveedorId;
    }
}
