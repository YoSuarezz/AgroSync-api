package com.agrosync.application.primaryports.dto.abonos.response;

import com.agrosync.application.primaryports.enums.cuentas.MetodoPagoEnum;
import com.agrosync.crosscutting.helpers.NumericHelper;
import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.crosscutting.helpers.TextHelper;
import com.agrosync.crosscutting.helpers.UUIDHelper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class ObtenerAbonoDTO {

    private UUID id;
    private UUID idCuentaPagar;
    private String numeroCuentaPagar;
    private BigDecimal monto;
    private LocalDateTime fechaPago;
    private MetodoPagoEnum metodoPago;
    private String concepto;

    public ObtenerAbonoDTO() {
        setId(UUIDHelper.getDefault());
        setIdCuentaPagar(UUIDHelper.getDefault());
        setNumeroCuentaPagar(TextHelper.EMPTY);
        setMonto(BigDecimal.ZERO);
        setFechaPago(LocalDateTime.now());
        setMetodoPago(null);
        setConcepto(TextHelper.EMPTY);
    }

    public ObtenerAbonoDTO(UUID id, UUID idCuentaPagar, String numeroCuentaPagar, BigDecimal monto,
            LocalDateTime fechaPago, MetodoPagoEnum metodoPago, String concepto) {
        setId(id);
        setIdCuentaPagar(idCuentaPagar);
        setNumeroCuentaPagar(numeroCuentaPagar);
        setMonto(monto);
        setFechaPago(fechaPago);
        setMetodoPago(metodoPago);
        setConcepto(concepto);
    }

    public static ObtenerAbonoDTO create(UUID id, UUID idCuentaPagar, String numeroCuentaPagar, BigDecimal monto,
            LocalDateTime fechaPago, MetodoPagoEnum metodoPago, String concepto) {
        return new ObtenerAbonoDTO(id, idCuentaPagar, numeroCuentaPagar, monto, fechaPago, metodoPago, concepto);
    }

    public static ObtenerAbonoDTO create() {
        return new ObtenerAbonoDTO();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = UUIDHelper.getDefault(id, UUIDHelper.getDefault());
    }

    public UUID getIdCuentaPagar() {
        return idCuentaPagar;
    }

    public void setIdCuentaPagar(UUID idCuentaPagar) {
        this.idCuentaPagar = UUIDHelper.getDefault(idCuentaPagar, UUIDHelper.getDefault());
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = NumericHelper.getDefault(monto, BigDecimal.ZERO);
    }

    public String getNumeroCuentaPagar() {
        return numeroCuentaPagar;
    }

    public void setNumeroCuentaPagar(String numeroCuentaPagar) {
        this.numeroCuentaPagar = TextHelper.applyTrim(numeroCuentaPagar);
    }

    public LocalDateTime getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = ObjectHelper.getDefault(fechaPago, LocalDateTime.now());
    }

    public MetodoPagoEnum getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPagoEnum metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getConcepto() {
        return TextHelper.applyTrim(concepto);
    }

    public void setConcepto(String concepto) {
        this.concepto = TextHelper.applyTrim(concepto);
    }
}
