package com.agrosync.application.primaryports.dto.cobros.request;

import com.agrosync.domain.enums.cuentas.MetodoPagoEnum;
import com.agrosync.crosscutting.helpers.NumericHelper;
import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.crosscutting.helpers.TextHelper;
import com.agrosync.crosscutting.helpers.UUIDHelper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class RegistrarCobroDTO {

    private UUID idCuentaCobrar;
    private BigDecimal monto;
    private LocalDateTime fechaCobro;
    private MetodoPagoEnum metodoPago;
    private String concepto;
    private UUID suscripcionId;

    public RegistrarCobroDTO() {
        setIdCuentaCobrar(UUIDHelper.getDefault());
        setMonto(BigDecimal.ZERO);
        setFechaCobro(LocalDateTime.now());
        setMetodoPago(MetodoPagoEnum.OTRO);
        setConcepto(TextHelper.EMPTY);
        setSuscripcionId(null);
    }

    public RegistrarCobroDTO(UUID idCuentaCobrar, BigDecimal monto, LocalDateTime fechaCobro, MetodoPagoEnum metodoPago, String concepto,
            UUID suscripcionId) {
        setIdCuentaCobrar(idCuentaCobrar);
        setMonto(monto);
        setFechaCobro(fechaCobro);
        setMetodoPago(metodoPago);
        setConcepto(concepto);
        setSuscripcionId(suscripcionId);
    }

    public static RegistrarCobroDTO create(UUID idCuentaCobrar, BigDecimal monto, LocalDateTime fechaCobro, MetodoPagoEnum metodoPago,
            String concepto, UUID suscripcionId) {
        return new RegistrarCobroDTO(idCuentaCobrar, monto, fechaCobro, metodoPago, concepto, suscripcionId);
    }

    public static RegistrarCobroDTO create() {
        return new RegistrarCobroDTO();
    }

    public UUID getIdCuentaCobrar() {
        return idCuentaCobrar;
    }

    public void setIdCuentaCobrar(UUID idCuentaCobrar) {
        this.idCuentaCobrar = UUIDHelper.getDefault(idCuentaCobrar, UUIDHelper.getDefault());
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = NumericHelper.getDefault(monto, BigDecimal.ZERO);
    }

    public LocalDateTime getFechaCobro() {
        return fechaCobro;
    }

    public void setFechaCobro(LocalDateTime fechaCobro) {
        this.fechaCobro = ObjectHelper.getDefault(fechaCobro, LocalDateTime.now());
    }

    public MetodoPagoEnum getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPagoEnum metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = TextHelper.applyTrim(concepto);
    }

    public UUID getSuscripcionId() {
        return suscripcionId;
    }

    public void setSuscripcionId(UUID suscripcionId) {
        this.suscripcionId = suscripcionId;
    }
}
