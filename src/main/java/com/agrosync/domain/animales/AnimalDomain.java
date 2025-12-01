package com.agrosync.domain.animales;

import com.agrosync.application.primaryports.enums.EstadoAnimalEnum;
import com.agrosync.application.primaryports.enums.SexoEnum;
import com.agrosync.domain.BaseDomain;
import com.agrosync.domain.lotes.LoteDomain;
import com.agrosync.domain.ventas.VentaDomain;

import java.math.BigDecimal;
import java.util.UUID;

public class AnimalDomain extends BaseDomain {

    private String slot;
    private String numeroAnimal;
    private BigDecimal peso;
    private SexoEnum sexo;
    private LoteDomain lote;
    private BigDecimal precioKiloCompra;
    private BigDecimal precioKiloVenta;
    private EstadoAnimalEnum estado;
    private VentaDomain venta;

    public AnimalDomain() {
        super();
    }

    public AnimalDomain(UUID id, String slot, String numeroAnimal, BigDecimal peso, SexoEnum sexo, LoteDomain lote, BigDecimal precioKiloCompra, BigDecimal precioKiloVenta, EstadoAnimalEnum estado, VentaDomain venta) {
        super(id);
        setSlot(slot);
        setNumeroAnimal(numeroAnimal);
        setPeso(peso);
        setSexo(sexo);
        setLote(lote);
        setPrecioKiloCompra(precioKiloCompra);
        setPrecioKiloVenta(precioKiloVenta);
        setEstado(estado);
        setVenta(venta);
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getNumeroAnimal() {
        return numeroAnimal;
    }

    public void setNumeroAnimal(String numeroAnimal) {
        this.numeroAnimal = numeroAnimal;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public SexoEnum getSexo() {
        return sexo;
    }

    public void setSexo(SexoEnum sexo) {
        this.sexo = sexo;
    }

    public LoteDomain getLote() {
        return lote;
    }

    public void setLote(LoteDomain lote) {
        this.lote = lote;
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

    public EstadoAnimalEnum getEstado() {
        return estado;
    }

    public void setEstado(EstadoAnimalEnum estado) {
        this.estado = estado;
    }

    public VentaDomain getVenta() {
        return venta;
    }

    public void setVenta(VentaDomain venta) {
        this.venta = venta;
    }
}
