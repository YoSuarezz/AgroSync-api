package com.agrosync.application.primaryports.dto.cobros.request;

import com.agrosync.application.primaryports.enums.cuentas.MetodoPagoEnum;
import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.crosscutting.helpers.TextHelper;

import java.math.BigDecimal;
import java.util.UUID;

public class RegistrarCobroDTO {

    private UUID idCuentaCobrar;
    private BigDecimal monto;
    private MetodoPagoEnum metodoPago;
    private String concepto;
    private UUID suscripcionId;

    public RegistrarCobroDTO() {
        setIdCuentaCobrar(null);
        setMonto(BigDecimal.ZERO);
        setMetodoPago(MetodoPagoEnum.OTRO);
        setConcepto(TextHelper.EMPTY);
        setSuscripcionId(null);
    }

    public RegistrarCobroDTO(UUID idCuentaCobrar, BigDecimal monto, MetodoPagoEnum metodoPago, String concepto,
            UUID suscripcionId) {
        setIdCuentaCobrar(idCuentaCobrar);
        setMonto(monto);
        setMetodoPago(metodoPago);
        setConcepto(concepto);
        setSuscripcionId(suscripcionId);
    }

    public static RegistrarCobroDTO create(UUID idCuentaCobrar, BigDecimal monto, MetodoPagoEnum metodoPago,
            String concepto, UUID suscripcionId) {
        return new RegistrarCobroDTO(idCuentaCobrar, monto, metodoPago, concepto, suscripcionId);
    }

    public static RegistrarCobroDTO create() {
        return new RegistrarCobroDTO();
    }

    public UUID getIdCuentaCobrar() {
        return idCuentaCobrar;
    }

    public RegistrarCobroDTO setIdCuentaCobrar(UUID idCuentaCobrar) {
        this.idCuentaCobrar = idCuentaCobrar;
        return this;
    }

    public BigDecimal getMonto() {
        return ObjectHelper.getDefault(monto, BigDecimal.ZERO);
    }

    public RegistrarCobroDTO setMonto(BigDecimal monto) {
        this.monto = ObjectHelper.getDefault(monto, BigDecimal.ZERO);
        return this;
    }

    public MetodoPagoEnum getMetodoPago() {
        return ObjectHelper.getDefault(metodoPago, MetodoPagoEnum.OTRO);
    }

    public RegistrarCobroDTO setMetodoPago(MetodoPagoEnum metodoPago) {
        this.metodoPago = ObjectHelper.getDefault(metodoPago, MetodoPagoEnum.OTRO);
        return this;
    }

    public String getConcepto() {
        return TextHelper.applyTrim(concepto);
    }

    public RegistrarCobroDTO setConcepto(String concepto) {
        this.concepto = TextHelper.applyTrim(concepto);
        return this;
    }

    public UUID getSuscripcionId() {
        return suscripcionId;
    }

    public RegistrarCobroDTO setSuscripcionId(UUID suscripcionId) {
        this.suscripcionId = suscripcionId;
        return this;
    }
}
