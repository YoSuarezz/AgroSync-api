package com.agrosync.domain.compras;

import com.agrosync.domain.BaseDomain;
import com.agrosync.domain.cuentaspagar.CuentaPagarDomain;
import com.agrosync.domain.lotes.LoteDomain;
import com.agrosync.domain.usuarios.UsuarioDomain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class CompraDomain extends BaseDomain {

    private String numeroCompra;
    private UsuarioDomain proveedor;
    private LocalDate fechaCompra;
    private BigDecimal precioTotalCompra;
    private LoteDomain lote;
    private CuentaPagarDomain cuentaPagar;
    private UUID suscripcionId;

    public CompraDomain() {
        super();
    }

    public CompraDomain(UUID id, String numeroCompra, UsuarioDomain proveedor, LocalDate fechaCompra, BigDecimal precioTotalCompra, LoteDomain lote, CuentaPagarDomain cuentaPagar, UUID suscripcionId) {
        super(id);
        setNumeroCompra(numeroCompra);
        setProveedor(proveedor);
        setFechaCompra(fechaCompra);
        setPrecioTotalCompra(precioTotalCompra);
        setLote(lote);
        setCuentaPagar(cuentaPagar);
        setSuscripcionId(suscripcionId);
    }

    public String getNumeroCompra() {
        return numeroCompra;
    }

    public void setNumeroCompra(String numeroCompra) {
        this.numeroCompra = numeroCompra;
    }

    public UsuarioDomain getProveedor() {
        return proveedor;
    }

    public void setProveedor(UsuarioDomain proveedor) {
        this.proveedor = proveedor;
    }

    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public BigDecimal getPrecioTotalCompra() {
        return precioTotalCompra;
    }

    public void setPrecioTotalCompra(BigDecimal precioTotalCompra) {
        this.precioTotalCompra = precioTotalCompra;
    }

    public LoteDomain getLote() {
        return lote;
    }

    public void setLote(LoteDomain lote) {
        this.lote = lote;
    }

    public CuentaPagarDomain getCuentaPagar() {
        return cuentaPagar;
    }

    public void setCuentaPagar(CuentaPagarDomain cuentaPagar) {
        this.cuentaPagar = cuentaPagar;
    }

    public UUID getSuscripcionId() {
        return suscripcionId;
    }

    public void setSuscripcionId(UUID suscripcionId) {
        this.suscripcionId = suscripcionId;
    }
}
