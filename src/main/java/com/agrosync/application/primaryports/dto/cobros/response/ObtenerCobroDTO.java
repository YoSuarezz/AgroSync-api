package com.agrosync.application.primaryports.dto.cobros.response;

import com.agrosync.application.primaryports.enums.cuentas.MetodoPagoEnum;
import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.crosscutting.helpers.TextHelper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class ObtenerCobroDTO {

    private UUID id;
    private UUID idCuentaCobrar;
    private String numeroCuentaCobrar;
    private BigDecimal monto;
    private LocalDateTime fechaCobro;
    private MetodoPagoEnum metodoPago;
    private String concepto;

    public ObtenerCobroDTO() {
        setId(null);
        setIdCuentaCobrar(null);
        setNumeroCuentaCobrar(null);
        setMonto(BigDecimal.ZERO);
        setFechaCobro(null);
        setMetodoPago(MetodoPagoEnum.OTRO);
        setConcepto(TextHelper.EMPTY);
    }

    public ObtenerCobroDTO(UUID id, UUID idCuentaCobrar, String numeroCuentaCobrar, BigDecimal monto,
            LocalDateTime fechaCobro, MetodoPagoEnum metodoPago, String concepto) {
        setId(id);
        setIdCuentaCobrar(idCuentaCobrar);
        setNumeroCuentaCobrar(numeroCuentaCobrar);
        setMonto(monto);
        setFechaCobro(fechaCobro);
        setMetodoPago(metodoPago);
        setConcepto(concepto);
    }

    public static ObtenerCobroDTO create(UUID id, UUID idCuentaCobrar, String numeroCuentaCobrar, BigDecimal monto,
            LocalDateTime fechaCobro, MetodoPagoEnum metodoPago, String concepto) {
        return new ObtenerCobroDTO(id, idCuentaCobrar, numeroCuentaCobrar, monto, fechaCobro, metodoPago, concepto);
    }

    public static ObtenerCobroDTO create() {
        return new ObtenerCobroDTO();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getIdCuentaCobrar() {
        return idCuentaCobrar;
    }

    public void setIdCuentaCobrar(UUID idCuentaCobrar) {
        this.idCuentaCobrar = idCuentaCobrar;
    }

    public String getNumeroCuentaCobrar() {
        return numeroCuentaCobrar;
    }

    public void setNumeroCuentaCobrar(String numeroCuentaCobrar) {
        this.numeroCuentaCobrar = numeroCuentaCobrar;
    }

    public BigDecimal getMonto() {
        return ObjectHelper.getDefault(monto, BigDecimal.ZERO);
    }

    public void setMonto(BigDecimal monto) {
        this.monto = ObjectHelper.getDefault(monto, BigDecimal.ZERO);
    }

    public LocalDateTime getFechaCobro() {
        return fechaCobro;
    }

    public void setFechaCobro(LocalDateTime fechaCobro) {
        this.fechaCobro = fechaCobro;
    }

    public MetodoPagoEnum getMetodoPago() {
        return ObjectHelper.getDefault(metodoPago, MetodoPagoEnum.OTRO);
    }

    public void setMetodoPago(MetodoPagoEnum metodoPago) {
        this.metodoPago = ObjectHelper.getDefault(metodoPago, MetodoPagoEnum.OTRO);
    }

    public String getConcepto() {
        return TextHelper.applyTrim(concepto);
    }

    public void setConcepto(String concepto) {
        this.concepto = TextHelper.applyTrim(concepto);
    }
}
