package com.agrosync.application.primaryports.dto.suscripcion.request;

import com.agrosync.domain.enums.suscripcion.EstadoSuscripcionEnum;

import java.time.LocalDateTime;
import java.util.UUID;

public class ActualizarEstadoSuscripcionDTO {

    private UUID id;
    private EstadoSuscripcionEnum estadoSuscripcion;
    private LocalDateTime fechaUltimoPago;

    public ActualizarEstadoSuscripcionDTO() {
    }

    public ActualizarEstadoSuscripcionDTO(UUID id, EstadoSuscripcionEnum estadoSuscripcion, LocalDateTime fechaUltimoPago) {
        setId(id);
        setEstadoSuscripcion(estadoSuscripcion);
        setFechaUltimoPago(fechaUltimoPago);
    }

    public static ActualizarEstadoSuscripcionDTO create(UUID id, EstadoSuscripcionEnum estadoSuscripcion, LocalDateTime fechaUltimoPago) {
        return new ActualizarEstadoSuscripcionDTO(id, estadoSuscripcion, fechaUltimoPago);
    }

    public static ActualizarEstadoSuscripcionDTO create() {
        return new ActualizarEstadoSuscripcionDTO();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public EstadoSuscripcionEnum getEstadoSuscripcion() {
        return estadoSuscripcion;
    }

    public void setEstadoSuscripcion(EstadoSuscripcionEnum estadoSuscripcion) {
        this.estadoSuscripcion = estadoSuscripcion;
    }

    public LocalDateTime getFechaUltimoPago() {
        return fechaUltimoPago;
    }

    public void setFechaUltimoPago(LocalDateTime fechaUltimoPago) {
        this.fechaUltimoPago = fechaUltimoPago;
    }
}
