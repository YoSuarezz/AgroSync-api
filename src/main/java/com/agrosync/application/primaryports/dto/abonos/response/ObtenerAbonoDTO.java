package com.agrosync.application.primaryports.dto.abonos.response;

import com.agrosync.application.primaryports.enums.cuentas.MetodoPagoEnum;
import com.agrosync.crosscutting.helpers.ObjectHelper;

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

    public ObtenerAbonoDTO() {
        setId(null);
        setIdCuentaPagar(null);
        setNumeroCuentaPagar(null);
        setMonto(BigDecimal.ZERO);
        setFechaPago(null);
        setMetodoPago(MetodoPagoEnum.OTRO);
    }

    public ObtenerAbonoDTO(UUID id, UUID idCuentaPagar, String numeroCuentaPagar, BigDecimal monto,
            LocalDateTime fechaPago, MetodoPagoEnum metodoPago) {
        setId(id);
        setIdCuentaPagar(idCuentaPagar);
        setNumeroCuentaPagar(numeroCuentaPagar);
        setMonto(monto);
        setFechaPago(fechaPago);
        setMetodoPago(metodoPago);
    }

    public static ObtenerAbonoDTO create(UUID id, UUID idCuentaPagar, String numeroCuentaPagar, BigDecimal monto,
            LocalDateTime fechaPago, MetodoPagoEnum metodoPago) {
        return new ObtenerAbonoDTO(id, idCuentaPagar, numeroCuentaPagar, monto, fechaPago, metodoPago);
    }

    public static ObtenerAbonoDTO create() {
        return new ObtenerAbonoDTO();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getIdCuentaPagar() {
        return idCuentaPagar;
    }

    public void setIdCuentaPagar(UUID idCuentaPagar) {
        this.idCuentaPagar = idCuentaPagar;
    }

    public String getNumeroCuentaPagar() {
        return numeroCuentaPagar;
    }

    public void setNumeroCuentaPagar(String numeroCuentaPagar) {
        this.numeroCuentaPagar = numeroCuentaPagar;
    }

    public BigDecimal getMonto() {
        return ObjectHelper.getDefault(monto, BigDecimal.ZERO);
    }

    public void setMonto(BigDecimal monto) {
        this.monto = ObjectHelper.getDefault(monto, BigDecimal.ZERO);
    }

    public LocalDateTime getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }

    public MetodoPagoEnum getMetodoPago() {
        return ObjectHelper.getDefault(metodoPago, MetodoPagoEnum.OTRO);
    }

    public void setMetodoPago(MetodoPagoEnum metodoPago) {
        this.metodoPago = ObjectHelper.getDefault(metodoPago, MetodoPagoEnum.OTRO);
    }
}
