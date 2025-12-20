package com.agrosync.domain.abonos;

import com.agrosync.domain.enums.cuentas.MetodoPagoEnum;
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
    private String concepto;
    private UUID suscripcionId;
    private String motivoAnulacion;

    public AbonoDomain() {
        super();
    }

    public AbonoDomain(UUID id, CuentaPagarDomain cuentaPagar, BigDecimal monto, LocalDateTime fechaPago, MetodoPagoEnum metodoPago, String concepto, UUID suscripcionId) {
        super(id);
        setCuentaPagar(cuentaPagar);
        setMonto(monto);
        setFechaPago(fechaPago);
        setMetodoPago(metodoPago);
        setConcepto(concepto);
        setSuscripcionId(suscripcionId);
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

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public UUID getSuscripcionId() {
        return suscripcionId;
    }

    public void setSuscripcionId(UUID suscripcionId) {
        this.suscripcionId = suscripcionId;
    }

    public String getMotivoAnulacion() {
        return motivoAnulacion;
    }

    public void setMotivoAnulacion(String motivoAnulacion) {
        this.motivoAnulacion = motivoAnulacion;
    }
}
