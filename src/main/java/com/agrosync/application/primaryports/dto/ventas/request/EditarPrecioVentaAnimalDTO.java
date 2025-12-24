package com.agrosync.application.primaryports.dto.ventas.request;

import com.agrosync.crosscutting.helpers.UUIDHelper;

import java.math.BigDecimal;
import java.util.UUID;

public class EditarPrecioVentaAnimalDTO {

    private UUID animalId;
    private BigDecimal nuevoPrecioKiloVenta;

    public EditarPrecioVentaAnimalDTO() {
        setAnimalId(UUIDHelper.getDefault());
        setNuevoPrecioKiloVenta(BigDecimal.ZERO);
    }

    public EditarPrecioVentaAnimalDTO(UUID animalId, BigDecimal nuevoPrecioKiloVenta) {
        setAnimalId(animalId);
        setNuevoPrecioKiloVenta(nuevoPrecioKiloVenta);
    }

    public static EditarPrecioVentaAnimalDTO create(UUID animalId, BigDecimal nuevoPrecioKiloVenta) {
        return new EditarPrecioVentaAnimalDTO(animalId, nuevoPrecioKiloVenta);
    }

    public static EditarPrecioVentaAnimalDTO create() {
        return new EditarPrecioVentaAnimalDTO();
    }

    public UUID getAnimalId() {
        return animalId;
    }

    public void setAnimalId(UUID animalId) {
        this.animalId = UUIDHelper.getDefault(animalId, UUIDHelper.getDefault());
    }

    public BigDecimal getNuevoPrecioKiloVenta() {
        return nuevoPrecioKiloVenta;
    }

    public void setNuevoPrecioKiloVenta(BigDecimal nuevoPrecioKiloVenta) {
        this.nuevoPrecioKiloVenta = nuevoPrecioKiloVenta;
    }
}
