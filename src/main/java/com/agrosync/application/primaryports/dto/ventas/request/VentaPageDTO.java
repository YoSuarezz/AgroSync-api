package com.agrosync.application.primaryports.dto.ventas.request;

import com.agrosync.application.primaryports.dto.ventas.response.ObtenerVentasDTO;
import com.agrosync.crosscutting.helpers.ObjectHelper;

import java.time.LocalDate;
import java.util.UUID;

public class VentaPageDTO {

    private int page;
    private int size;
    private String sortBy;
    private String sortDirection;
    private ObtenerVentasDTO venta;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String numeroVenta;
    private UUID suscripcionId;
    private UUID clienteId;

    public VentaPageDTO() {
    }

    public VentaPageDTO(int page, int size, String sortBy, String sortDirection, ObtenerVentasDTO venta, LocalDate fechaInicio, LocalDate fechaFin, String numeroVenta, UUID suscripcionId, UUID clienteId) {
        setPage(page);
        setSize(size);
        setSortBy(sortBy);
        setSortDirection(sortDirection);
        setVenta(venta);
        setFechaInicio(fechaInicio);
        setFechaFin(fechaFin);
        setNumeroVenta(numeroVenta);
        setSuscripcionId(suscripcionId);
        setClienteId(clienteId);
    }

    public static VentaPageDTO create(int page, int size, String sortBy, String sortDirection, ObtenerVentasDTO venta, LocalDate fechaInicio, LocalDate fechaFin, String numeroVenta, UUID suscripcionId, UUID clienteId) {
        return new VentaPageDTO(page, size, sortBy, sortDirection, venta, fechaInicio, fechaFin, numeroVenta, suscripcionId, clienteId);
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

    public ObtenerVentasDTO getVenta() {
        return venta;
    }

    public void setVenta(ObtenerVentasDTO venta) {
        this.venta = ObjectHelper.getDefault(venta, ObtenerVentasDTO.create());
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

    public String getNumeroVenta() {
        return numeroVenta;
    }

    public void setNumeroVenta(String numeroVenta) {
        this.numeroVenta = numeroVenta;
    }

    public UUID getSuscripcionId() {
        return suscripcionId;
    }

    public void setSuscripcionId(UUID suscripcionId) {
        this.suscripcionId = suscripcionId;
    }

    public UUID getClienteId() {
        return clienteId;
    }

    public void setClienteId(UUID clienteId) {
        this.clienteId = clienteId;
    }

}
