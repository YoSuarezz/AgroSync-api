package com.agrosync.application.primaryports.dto.animales.response;

import com.agrosync.domain.enums.animales.EstadoAnimalEnum;
import com.agrosync.domain.enums.animales.SexoEnum;
import com.agrosync.crosscutting.helpers.NumericHelper;
import com.agrosync.crosscutting.helpers.TextHelper;
import com.agrosync.crosscutting.helpers.UUIDHelper;

import java.math.BigDecimal;
import java.util.UUID;

public class ObtenerAnimalDTO {

    private UUID id;
    private String numeroAnimal;
    private SexoEnum sexo;
    private BigDecimal peso;
    private EstadoAnimalEnum estado;
    private BigDecimal precioKiloCompra;
    private BigDecimal precioKiloVenta;

    public ObtenerAnimalDTO() {
        setId(UUIDHelper.getDefault());
        setNumeroAnimal(TextHelper.EMPTY);
        setSexo(null);
        setPeso(BigDecimal.ZERO);
        setEstado(null);
        setPrecioKiloCompra(null);
        setPrecioKiloVenta(null);
    }

    public ObtenerAnimalDTO(UUID id, String numeroAnimal, SexoEnum sexo, BigDecimal peso, EstadoAnimalEnum estado,
                           BigDecimal precioKiloCompra, BigDecimal precioKiloVenta) {
        setId(id);
        setNumeroAnimal(numeroAnimal);
        setSexo(sexo);
        setPeso(peso);
        setEstado(estado);
        setPrecioKiloCompra(precioKiloCompra);
        setPrecioKiloVenta(precioKiloVenta);
    }

    public static ObtenerAnimalDTO create(UUID id, String numeroAnimal, SexoEnum sexo, BigDecimal peso, EstadoAnimalEnum estado,
                                          BigDecimal precioKiloCompra, BigDecimal precioKiloVenta) {
        return new ObtenerAnimalDTO(id, numeroAnimal, sexo, peso, estado, precioKiloCompra, precioKiloVenta);
    }

    public static ObtenerAnimalDTO create(UUID id, String numeroAnimal, SexoEnum sexo, BigDecimal peso, EstadoAnimalEnum estado) {
        return new ObtenerAnimalDTO(id, numeroAnimal, sexo, peso, estado, null, null);
    }

    public static ObtenerAnimalDTO create() {
        return new ObtenerAnimalDTO();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = UUIDHelper.getDefault(id, UUIDHelper.getDefault());
    }

    public String getNumeroAnimal() {
        return numeroAnimal;
    }

    public void setNumeroAnimal(String numeroAnimal) {
        this.numeroAnimal = TextHelper.applyTrim(numeroAnimal);
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
        this.peso = NumericHelper.getDefault(peso, BigDecimal.ZERO);
    }

    public EstadoAnimalEnum getEstado() {
        return estado;
    }

    public void setEstado(EstadoAnimalEnum estado) {
        this.estado = estado;
    }

    public BigDecimal getPrecioKiloCompra() {
        return precioKiloCompra;
    }

    public void setPrecioKiloCompra(BigDecimal precioKiloCompra) {
        this.precioKiloCompra = precioKiloCompra;
    }

    public BigDecimal getPrecioKiloVenta() {
        return precioKiloVenta;
    }

    public void setPrecioKiloVenta(BigDecimal precioKiloVenta) {
        this.precioKiloVenta = precioKiloVenta;
    }
}
