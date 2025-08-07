package com.agrosync.application.secondaryports.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "animal")
public class AnimalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_animal")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_lote")
    private LoteEntity lote;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "venta_id_venta")  // Permite null
    private VentaEntity venta;

    @Column(name = "peso")
    private BigDecimal peso;

    @Column(name = "sexo", length = 40)
    private String sexo;

    @Column(name = "slot")
    private Integer slot;

    @Column(name = "precio_kilo_compra")
    private BigDecimal precioKiloCompra;

    @Column(name = "precio_kilo_venta")
    private BigDecimal precioKiloVenta;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrecioKiloVenta() {
        return precioKiloVenta;
    }

    public void setPrecioKiloVenta(BigDecimal precioKiloVenta) {
        this.precioKiloVenta = precioKiloVenta;
    }

    public BigDecimal getPrecioKiloCompra() {
        return precioKiloCompra;
    }

    public void setPrecioKiloCompra(BigDecimal precioKiloCompra) {
        this.precioKiloCompra = precioKiloCompra;
    }

    public Integer getSlot() {
        return slot;
    }

    public void setSlot(Integer slot) {
        this.slot = slot;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public VentaEntity getVenta() {
        return venta;
    }

    public void setVenta(VentaEntity venta) {
        this.venta = venta;
    }

    public LoteEntity getLote() {
        return lote;
    }

    public void setLote(LoteEntity lote) {
        this.lote = lote;
    }
}
