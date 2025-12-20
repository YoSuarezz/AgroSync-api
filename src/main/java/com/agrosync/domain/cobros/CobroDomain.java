package com.agrosync.domain.cobros;

import com.agrosync.domain.enums.cuentas.MetodoPagoEnum;
import com.agrosync.domain.BaseDomain;
import com.agrosync.domain.cuentascobrar.CuentaCobrarDomain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class CobroDomain extends BaseDomain {

    private CuentaCobrarDomain cuentaCobrar;
    private BigDecimal monto;
    private LocalDateTime fechaCobro;
    private MetodoPagoEnum metodoPago;
    private String concepto;
    private UUID suscripcionId;
    private String motivoAnulacion;

    public CobroDomain() {
        super();
    }

    public CobroDomain(UUID id, CuentaCobrarDomain cuentaCobrar, BigDecimal monto, LocalDateTime fechaCobro, MetodoPagoEnum metodoPago, String concepto, UUID suscripcionId) {
        super(id);
        setCuentaCobrar(cuentaCobrar);
        setMonto(monto);
        setFechaCobro(fechaCobro);
        setMetodoPago(metodoPago);
        setConcepto(concepto);
        setSuscripcionId(suscripcionId);
    }

    public CuentaCobrarDomain getCuentaCobrar() {
        return cuentaCobrar;
    }

    public void setCuentaCobrar(CuentaCobrarDomain cuentaCobrar) {
        this.cuentaCobrar = cuentaCobrar;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public LocalDateTime getFechaCobro() {
        return fechaCobro;
    }

    public void setFechaCobro(LocalDateTime fechaCobro) {
        this.fechaCobro = fechaCobro;
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
