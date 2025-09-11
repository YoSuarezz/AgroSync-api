package com.agrosync.application.secondaryports.entity.gastos;

import com.agrosync.application.secondaryports.entity.LoteEntity;
import com.agrosync.crosscutting.helpers.NumericHelper;
import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.crosscutting.helpers.TextHelper;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "gasto")
public class GastoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_gasto")
    private Long id;

    @JoinColumn(name = "id_lote")
    @ManyToOne
    private LoteEntity lote;

    @Column(name = "cantidad")
    private BigDecimal cantidad;

    @Column(name = "descripcion")
    @Lob
    private String descripcion;

    @Column(name = "fecha")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;

    public GastoEntity() {
        setId(id);
        setLote(lote);
        setCantidad(BigDecimal.ZERO);
        setDescripcion(TextHelper.EMPTY);
        setFecha(LocalDate.now());
    }

    public GastoEntity(Long id, LoteEntity lote, BigDecimal cantidad, String descripcion, LocalDate fecha) {
        setId(id);
        setLote(lote);
        setCantidad(cantidad);
        setDescripcion(descripcion);
        setFecha(fecha);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LoteEntity getLote() {
        return lote;
    }

    public void setLote(LoteEntity lote) {
        this.lote = ObjectHelper.getDefault(lote, new LoteEntity());
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = TextHelper.applyTrim(descripcion);
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
