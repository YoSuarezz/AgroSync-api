package com.agrosync.application.primaryports.dto.animales.request;

import com.agrosync.application.primaryports.enums.animales.SexoEnum;
import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.crosscutting.helpers.TextHelper;
import com.agrosync.crosscutting.helpers.UUIDHelper;

import java.math.BigDecimal;
import java.util.UUID;

public class RegistrarNuevoAnimalDTO {

    private String slot;
    private BigDecimal peso;
    private SexoEnum sexo;
    private BigDecimal precioKiloCompra;
    private UUID suscripcionId;

    public RegistrarNuevoAnimalDTO() {
        setSlot(TextHelper.EMPTY);
        setPeso(BigDecimal.ZERO);
        setSexo(SexoEnum.MACHO);
        setPrecioKiloCompra(BigDecimal.ZERO);
        setSuscripcionId(UUIDHelper.getDefault());
    }

    public RegistrarNuevoAnimalDTO(String slot, BigDecimal peso, SexoEnum sexo, BigDecimal precioKiloCompra, UUID suscripcionId) {
        setSlot(slot);
        setPeso(peso);
        setSexo(sexo);
        setPrecioKiloCompra(precioKiloCompra);
        setSuscripcionId(suscripcionId);
    }

    public static RegistrarNuevoAnimalDTO create(String slot, BigDecimal peso, SexoEnum sexo, BigDecimal precioKiloCompra, UUID suscripcionId) {
        return new RegistrarNuevoAnimalDTO(slot, peso, sexo, precioKiloCompra, suscripcionId);
    }

    public static RegistrarNuevoAnimalDTO create() {
        return new RegistrarNuevoAnimalDTO();
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = TextHelper.applyTrim(slot);
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = ObjectHelper.getDefault(peso, BigDecimal.ZERO);
    }

    public SexoEnum getSexo() {
        return sexo;
    }

    public void setSexo(SexoEnum sexo) {
        this.sexo = ObjectHelper.getDefault(sexo, SexoEnum.MACHO);
    }

    public BigDecimal getPrecioKiloCompra() {
        return precioKiloCompra;
    }

    public void setPrecioKiloCompra(BigDecimal precioKiloCompra) {
        this.precioKiloCompra = ObjectHelper.getDefault(precioKiloCompra, BigDecimal.ZERO);
    }

    public UUID getSuscripcionId() {
        return suscripcionId;
    }

    public void setSuscripcionId(UUID suscripcionId) {
        this.suscripcionId = UUIDHelper.getDefault(suscripcionId, UUIDHelper.getDefault());
    }
}
