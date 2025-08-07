package com.agrosync.application.primaryports.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
@Getter @Setter
public class AnimalDTO {
    private Long id;
    private Long idLote;
    private Long idVenta;
    private BigDecimal peso;
    private String sexo;
    private Integer slot;
    private BigDecimal precioKiloCompra;
    private BigDecimal precioKiloVenta;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdLote() {
        return idLote;
    }

    public void setIdLote(Long idLote) {
        this.idLote = idLote;
    }

    public Long getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Long idVenta) {
        this.idVenta = idVenta;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Integer getSlot() {
        return slot;
    }

    public void setSlot(Integer slot) {
        this.slot = slot;
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
