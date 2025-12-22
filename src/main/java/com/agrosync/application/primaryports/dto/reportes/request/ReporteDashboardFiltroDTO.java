package com.agrosync.application.primaryports.dto.reportes.request;

import com.agrosync.domain.enums.cuentas.MetodoPagoEnum;

import java.time.LocalDate;
import java.util.UUID;

public class ReporteDashboardFiltroDTO {

    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private MetodoPagoEnum metodoPago;
    private UUID clienteId;
    private UUID proveedorId;
    private UUID suscripcionId;

    public ReporteDashboardFiltroDTO() {
    }

    public ReporteDashboardFiltroDTO(LocalDate fechaInicio, LocalDate fechaFin, MetodoPagoEnum metodoPago, UUID clienteId, UUID proveedorId, UUID suscripcionId) {
        setFechaInicio(fechaInicio);
        setFechaFin(fechaFin);
        setMetodoPago(metodoPago);
        setClienteId(clienteId);
        setProveedorId(proveedorId);
        setSuscripcionId(suscripcionId);
    }

    public static ReporteDashboardFiltroDTO create(LocalDate fechaInicio, LocalDate fechaFin, MetodoPagoEnum metodoPago, UUID clienteId, UUID proveedorId, UUID suscripcionId) {
        return new ReporteDashboardFiltroDTO(fechaInicio, fechaFin, metodoPago, clienteId, proveedorId, suscripcionId);
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

    public MetodoPagoEnum getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPagoEnum metodoPago) {
        this.metodoPago = metodoPago;
    }

    public UUID getClienteId() {
        return clienteId;
    }

    public void setClienteId(UUID clienteId) {
        this.clienteId = clienteId;
    }

    public UUID getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(UUID proveedorId) {
        this.proveedorId = proveedorId;
    }

    public UUID getSuscripcionId() {
        return suscripcionId;
    }

    public void setSuscripcionId(UUID suscripcionId) {
        this.suscripcionId = suscripcionId;
    }
}
