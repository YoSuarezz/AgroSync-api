package com.agrosync.application.primaryports.dto.lotes.response;

import com.agrosync.crosscutting.helpers.DateHelper;
import com.agrosync.crosscutting.helpers.NumericHelper;
import com.agrosync.crosscutting.helpers.TextHelper;
import com.agrosync.crosscutting.helpers.UUIDHelper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class ObtenerLoteDTO {

    private UUID id;
    private String numeroLote;
    private LocalDate fecha;
    private BigDecimal pesoTotal;

    public ObtenerLoteDTO() {
        setId(UUIDHelper.getDefault());
        setNumeroLote(TextHelper.EMPTY);
        setFecha(DateHelper.getDefault());
        setPesoTotal(BigDecimal.ZERO);
    }

    public ObtenerLoteDTO(UUID id, String numeroLote, LocalDate fecha, BigDecimal pesoTotal) {
        setId(id);
        setNumeroLote(numeroLote);
        setFecha(fecha);
        setPesoTotal(pesoTotal);
    }

    public static ObtenerLoteDTO create(UUID id, String numeroLote, LocalDate fecha, BigDecimal pesoTotal) {
        return new ObtenerLoteDTO(id, numeroLote, fecha, pesoTotal);
    }

    public static ObtenerLoteDTO create() {
        return new ObtenerLoteDTO();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = UUIDHelper.getDefault(id, UUIDHelper.getDefault());
    }

    public String getNumeroLote() {
        return numeroLote;
    }

    public void setNumeroLote(String numeroLote) {
        this.numeroLote = TextHelper.applyTrim(numeroLote);
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = DateHelper.getDefault(fecha, DateHelper.getDefault());
    }

    public BigDecimal getPesoTotal() {
        return pesoTotal;
    }

    public void setPesoTotal(BigDecimal pesoTotal) {
        this.pesoTotal = NumericHelper.getDefault(pesoTotal, BigDecimal.ZERO);
    }
}
