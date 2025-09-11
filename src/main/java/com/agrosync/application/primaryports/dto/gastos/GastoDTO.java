package com.agrosync.application.primaryports.dto.gastos;

import com.agrosync.application.primaryports.dto.LoteDTO;
import com.agrosync.crosscutting.helpers.NumericHelper;
import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.crosscutting.helpers.TextHelper;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class GastoDTO {

    private Long id;
    private LoteDTO lote;
    private BigDecimal cantidad;
    private String descripcion;
    private LocalDate fecha;

    public GastoDTO() {
        setId(id);
        setLote(lote);
        setCantidad(BigDecimal.ZERO);
        setDescripcion(TextHelper.EMPTY);
        setFecha(LocalDate.now());
    }

    public GastoDTO(Long id, LoteDTO lote, BigDecimal cantidad, String descripcion) {
        this.id = id;
        this.lote = lote;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
        this.fecha = LocalDate.now();
    }

    public static GastoDTO create(Long id, LoteDTO lote, BigDecimal cantidad, String descripcion) {
        return new GastoDTO(id, lote, cantidad, descripcion);
    }

    public static GastoDTO create() {
        return new GastoDTO();
    }

    public Long getId() {
        return id;
    }

    public GastoDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public LoteDTO getLote() {
        return lote;
    }

    public GastoDTO setLote(LoteDTO lote) {
        this.lote = ObjectHelper.getDefault(lote, new LoteDTO());
        return this;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public GastoDTO setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public GastoDTO setDescripcion(String descripcion) {
        this.descripcion = TextHelper.applyTrim(descripcion);
        return this;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public GastoDTO setFecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }
}

