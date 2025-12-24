package com.agrosync.application.primaryports.dto.animales.request;

import com.agrosync.crosscutting.helpers.UUIDHelper;
import com.agrosync.domain.enums.animales.SexoEnum;

import java.math.BigDecimal;
import java.util.UUID;

public class EditarAnimalDTO {

    private UUID id;
    private SexoEnum sexo;
    private BigDecimal peso;
    private BigDecimal precioKiloCompra;
    private UUID suscripcionId;

    public EditarAnimalDTO() {
        setId(UUIDHelper.getDefault());
        setSexo(null);
        setPeso(null);
        setPrecioKiloCompra(null);
        setSuscripcionId(UUIDHelper.getDefault());
    }

    public EditarAnimalDTO(UUID id, SexoEnum sexo, BigDecimal peso, BigDecimal precioKiloCompra, UUID suscripcionId) {
        setId(id);
        setSexo(sexo);
        setPeso(peso);
        setPrecioKiloCompra(precioKiloCompra);
        setSuscripcionId(suscripcionId);
    }

    public static EditarAnimalDTO create(UUID id, SexoEnum sexo, BigDecimal peso, BigDecimal precioKiloCompra,
            UUID suscripcionId) {
        return new EditarAnimalDTO(id, sexo, peso, precioKiloCompra, suscripcionId);
    }

    public static EditarAnimalDTO create() {
        return new EditarAnimalDTO();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = UUIDHelper.getDefault(id, UUIDHelper.getDefault());
    }

    public SexoEnum getSexo() {
        return sexo;
    }

    public void setSexo(SexoEnum sexo) {
        this.sexo = sexo;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public BigDecimal getPrecioKiloCompra() {
        return precioKiloCompra;
    }

    public void setPrecioKiloCompra(BigDecimal precioKiloCompra) {
        this.precioKiloCompra = precioKiloCompra;
    }

    public UUID getSuscripcionId() {
        return suscripcionId;
    }

    public void setSuscripcionId(UUID suscripcionId) {
        this.suscripcionId = UUIDHelper.getDefault(suscripcionId, UUIDHelper.getDefault());
    }
}
