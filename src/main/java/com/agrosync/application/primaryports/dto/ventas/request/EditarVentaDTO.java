package com.agrosync.application.primaryports.dto.ventas.request;

import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.crosscutting.helpers.UUIDHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EditarVentaDTO {

    private UUID ventaId;
    private List<EditarPrecioVentaAnimalDTO> animales;
    private UUID suscripcionId;

    public EditarVentaDTO() {
        setVentaId(UUIDHelper.getDefault());
        setAnimales(new ArrayList<>());
        setSuscripcionId(UUIDHelper.getDefault());
    }

    public EditarVentaDTO(UUID ventaId, List<EditarPrecioVentaAnimalDTO> animales, UUID suscripcionId) {
        setVentaId(ventaId);
        setAnimales(animales);
        setSuscripcionId(suscripcionId);
    }

    public static EditarVentaDTO create(UUID ventaId, List<EditarPrecioVentaAnimalDTO> animales, UUID suscripcionId) {
        return new EditarVentaDTO(ventaId, animales, suscripcionId);
    }

    public static EditarVentaDTO create() {
        return new EditarVentaDTO();
    }

    public UUID getVentaId() {
        return ventaId;
    }

    public void setVentaId(UUID ventaId) {
        this.ventaId = UUIDHelper.getDefault(ventaId, UUIDHelper.getDefault());
    }

    public List<EditarPrecioVentaAnimalDTO> getAnimales() {
        return animales;
    }

    public void setAnimales(List<EditarPrecioVentaAnimalDTO> animales) {
        this.animales = ObjectHelper.getDefault(animales, new ArrayList<>());
    }

    public UUID getSuscripcionId() {
        return suscripcionId;
    }

    public void setSuscripcionId(UUID suscripcionId) {
        this.suscripcionId = UUIDHelper.getDefault(suscripcionId, UUIDHelper.getDefault());
    }
}
