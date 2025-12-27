package com.agrosync.application.primaryports.dto.usuarios.request;

import java.time.LocalDate;
import java.util.UUID;

public class UsuarioDetalladoFiltroDTO {

    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private boolean saldoPendiente;
    private UUID usuarioId;
    private UUID suscripcionId;

    public UsuarioDetalladoFiltroDTO(LocalDate fechaInicio, LocalDate fechaFin, boolean saldoPendiente, UUID usuarioId, UUID suscripcionId) {
        setFechaInicio(fechaInicio);
        setFechaFin(fechaFin);
        setSaldoPendiente(saldoPendiente);
        setUsuarioId(usuarioId);
        setSuscripcionId(suscripcionId);
    }

    public static UsuarioDetalladoFiltroDTO create(LocalDate fechaInicio, LocalDate fechaFin, boolean saldoPendiente, UUID usuarioId, UUID suscripcionId) {
        return new UsuarioDetalladoFiltroDTO(fechaInicio, fechaFin, saldoPendiente, usuarioId, suscripcionId);
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

    public boolean isSaldoPendiente() {
        return saldoPendiente;
    }

    public void setSaldoPendiente(boolean saldoPendiente) {
        this.saldoPendiente = saldoPendiente;
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(UUID usuarioId) {
        this.usuarioId = usuarioId;
    }

    public UUID getSuscripcionId() {
        return suscripcionId;
    }

    public void setSuscripcionId(UUID suscripcionId) {
        this.suscripcionId = suscripcionId;
    }
}
