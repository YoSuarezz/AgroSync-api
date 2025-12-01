package com.agrosync.domain.abonos;

import com.agrosync.application.primaryports.enums.MetodoPagoEnum;
import com.agrosync.domain.BaseDomain;
import com.agrosync.domain.cuentaspagar.CuentaPagarDomain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class AbonoDomain extends BaseDomain {

    private CuentaPagarDomain cuentaPagar;
    private BigDecimal monto;
    private LocalDateTime fechaPago;
    private MetodoPagoEnum metodoPago;

    public AbonoDomain() {
        super();
    }

    public AbonoDomain(UUID id, CuentaPagarDomain cuentaPagar, BigDecimal monto, LocalDateTime fechaPago, MetodoPagoEnum metodoPago) {
        super(id);
        setCuentaPagar(cuentaPagar);
        setMonto(monto);
        setFechaPago(fechaPago);
        setMetodoPago(metodoPago);
    }

    public CuentaPagarDomain getCuentaPagar() {
        return cuentaPagar;
    }

    public void setCuentaPagar(CuentaPagarDomain cuentaPagar) {
        this.cuentaPagar = cuentaPagar;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public LocalDateTime getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }

    public MetodoPagoEnum getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPagoEnum metodoPago) {
        this.metodoPago = metodoPago;
    }
}
