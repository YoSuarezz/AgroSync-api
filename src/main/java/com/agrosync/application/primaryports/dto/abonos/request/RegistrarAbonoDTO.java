package com.agrosync.application.primaryports.dto.abonos.request;

import com.agrosync.application.primaryports.enums.cuentas.MetodoPagoEnum;
import com.agrosync.crosscutting.helpers.NumericHelper;
import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.crosscutting.helpers.TextHelper;
import com.agrosync.crosscutting.helpers.UUIDHelper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class RegistrarAbonoDTO {

    private UUID idCuentaPagar;
    private BigDecimal monto;
    private LocalDateTime fechaPago;
    private MetodoPagoEnum metodoPago;
    private String concepto;
    private UUID suscripcionId;

    public RegistrarAbonoDTO() {
        setIdCuentaPagar(UUIDHelper.getDefault());
        setMonto(BigDecimal.ZERO);
        setFechaPago(LocalDateTime.now());
        setMetodoPago(MetodoPagoEnum.OTRO);
        setConcepto(TextHelper.EMPTY);
        setSuscripcionId(null);
    }

    public RegistrarAbonoDTO(UUID idCuentaPagar, BigDecimal monto, LocalDateTime fechaPago, MetodoPagoEnum metodoPago, String concepto, UUID suscripcionId) {
        setIdCuentaPagar(idCuentaPagar);
        setMonto(monto);
        setFechaPago(fechaPago);
        setMetodoPago(metodoPago);
        setConcepto(concepto);
        setSuscripcionId(suscripcionId);
    }

    public static RegistrarAbonoDTO create(UUID idCuentaPagar, BigDecimal monto, LocalDateTime fechaPago, MetodoPagoEnum metodoPago,
            String concepto, UUID suscripcionId) {
        return new RegistrarAbonoDTO(idCuentaPagar, monto, fechaPago, metodoPago, concepto, suscripcionId);
    }

    public static RegistrarAbonoDTO create() {
        return new RegistrarAbonoDTO();
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
