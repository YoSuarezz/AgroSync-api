package com.agrosync.domain.cuentaspagar;

import com.agrosync.application.primaryports.enums.EstadoCuenta;
import com.agrosync.domain.BaseDomain;
import com.agrosync.domain.abonos.AbonoDomain;
import com.agrosync.domain.compras.CompraDomain;
import com.agrosync.domain.usuarios.UsuarioDomain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class CuentaPagarDomain extends BaseDomain {

    private String numeroCuenta;
    private CompraDomain compra;
    private UsuarioDomain proveedor;
    private BigDecimal montoTotal;
    private BigDecimal saldoPendiente;
    private List<AbonoDomain> abonos;
    private EstadoCuenta estado;
    private LocalDate fechaEmision;
    private LocalDate fechaVencimiento;

    public CuentaPagarDomain() {
        super();
    }

    public CuentaPagarDomain(UUID id, String numeroCuenta, CompraDomain compra, UsuarioDomain proveedor, BigDecimal montoTotal, BigDecimal saldoPendiente, List<AbonoDomain> abonos, EstadoCuenta estado, LocalDate fechaEmision, LocalDate fechaVencimiento) {
        super(id);
        setNumeroCuenta(numeroCuenta);
        setCompra(compra);
        setProveedor(proveedor);
        setMontoTotal(montoTotal);
        setSaldoPendiente(saldoPendiente);
        setAbonos(abonos);
        setEstado(estado);
        setFechaEmision(fechaEmision);
        setFechaVencimiento(fechaVencimiento);
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public CompraDomain getCompra() {
        return compra;
    }

    public void setCompra(CompraDomain compra) {
        this.compra = compra;
    }

    public UsuarioDomain getProveedor() {
        return proveedor;
    }

    public void setProveedor(UsuarioDomain proveedor) {
        this.proveedor = proveedor;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    public BigDecimal getSaldoPendiente() {
        return saldoPendiente;
    }

    public void setSaldoPendiente(BigDecimal saldoPendiente) {
        this.saldoPendiente = saldoPendiente;
    }

    public List<AbonoDomain> getAbonos() {
        return abonos;
    }

    public void setAbonos(List<AbonoDomain> abonos) {
        this.abonos = abonos;
    }

    public EstadoCuenta getEstado() {
        return estado;
    }

    public void setEstado(EstadoCuenta estado) {
        this.estado = estado;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
}
