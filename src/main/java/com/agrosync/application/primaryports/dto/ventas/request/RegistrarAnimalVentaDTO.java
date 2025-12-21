package com.agrosync.application.primaryports.dto.ventas.request;

import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.crosscutting.helpers.UUIDHelper;

import java.math.BigDecimal;
import java.util.UUID;

public class RegistrarAnimalVentaDTO {

    private UUID animalId;
    private BigDecimal precioKiloVenta;
    private UUID suscripcionId;

    public RegistrarAnimalVentaDTO() {
        setAnimalId(UUIDHelper.getDefault());
        setPrecioKiloVenta(BigDecimal.ZERO);
        setSuscripcionId(UUIDHelper.getDefault());
    }

    public RegistrarAnimalVentaDTO(UUID animalId, BigDecimal precioKiloVenta, UUID suscripcionId) {
        setAnimalId(animalId);
        setPrecioKiloVenta(precioKiloVenta);
        setSuscripcionId(suscripcionId);
    }

    public static RegistrarAnimalVentaDTO create(UUID animalId, BigDecimal precioKiloVenta, UUID suscripcionId) {
        return new RegistrarAnimalVentaDTO(animalId, precioKiloVenta, suscripcionId);
    }

    public static RegistrarAnimalVentaDTO create() {
        return new RegistrarAnimalVentaDTO();
    }

    public UUID getAnimalId() {
        return animalId;
    }

    public void setAnimalId(UUID animalId) {
        this.animalId = UUIDHelper.getDefault(animalId, UUIDHelper.getDefault());
    }

    public BigDecimal getPrecioKiloVenta() {
        return precioKiloVenta;
    }

    public void setPrecioKiloVenta(BigDecimal precioKiloVenta) {
        this.precioKiloVenta = ObjectHelper.getDefault(precioKiloVenta, BigDecimal.ZERO);
    }

    public UUID getSuscripcionId() {
        return suscripcionId;
    }

    public void setSuscripcionId(UUID suscripcionId) {
        this.suscripcionId = UUIDHelper.getDefault(suscripcionId, UUIDHelper.getDefault());
    }
}
