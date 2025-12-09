package com.agrosync.application.primaryports.dto.ventas.request;

import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.crosscutting.helpers.UUIDHelper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RegistrarNuevaVentaDTO {

    private UUID clienteId;
    private LocalDate fechaVenta;
    private List<RegistrarAnimalVentaDTO> animales;
    private UUID suscripcionId;

    public RegistrarNuevaVentaDTO() {
        setClienteId(UUIDHelper.getDefault());
        setFechaVenta(LocalDate.now());
        setAnimales(new ArrayList<>());
        setSuscripcionId(UUIDHelper.getDefault());
    }

    public RegistrarNuevaVentaDTO(UUID clienteId, LocalDate fechaVenta, List<RegistrarAnimalVentaDTO> animales, UUID suscripcionId) {
        setClienteId(clienteId);
        setFechaVenta(fechaVenta);
        setAnimales(animales);
        setSuscripcionId(suscripcionId);
    }

    public static RegistrarNuevaVentaDTO create(UUID clienteId, LocalDate fechaVenta, List<RegistrarAnimalVentaDTO> animales, UUID suscripcionId) {
        return new RegistrarNuevaVentaDTO(clienteId, fechaVenta, animales, suscripcionId);
    }

    public static RegistrarNuevaVentaDTO create() {
        return new RegistrarNuevaVentaDTO();
    }

    public UUID getClienteId() {
        return clienteId;
    }

    public void setClienteId(UUID clienteId) {
        this.clienteId = UUIDHelper.getDefault(clienteId, UUIDHelper.getDefault());
    }

    public LocalDate getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(LocalDate fechaVenta) {
        this.fechaVenta = ObjectHelper.getDefault(fechaVenta, LocalDate.now());
    }

    public List<RegistrarAnimalVentaDTO> getAnimales() {
        return animales;
    }

    public void setAnimales(List<RegistrarAnimalVentaDTO> animales) {
        this.animales = ObjectHelper.getDefault(animales, new ArrayList<>());
    }

    public UUID getSuscripcionId() {
        return suscripcionId;
    }

    public void setSuscripcionId(UUID suscripcionId) {
        this.suscripcionId = UUIDHelper.getDefault(suscripcionId, UUIDHelper.getDefault());
    }
}
