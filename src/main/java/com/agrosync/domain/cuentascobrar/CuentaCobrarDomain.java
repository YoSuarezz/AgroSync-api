package com.agrosync.domain.cuentascobrar;

import com.agrosync.application.primaryports.enums.EstadoCuenta;
import com.agrosync.domain.BaseDomain;
import com.agrosync.domain.cobros.CobroDomain;
import com.agrosync.domain.usuarios.UsuarioDomain;
import com.agrosync.domain.ventas.VentaDomain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class CuentaCobrarDomain extends BaseDomain {

    private String numeroCuenta;
    private VentaDomain venta;
    private UsuarioDomain cliente;
    private BigDecimal montoTotal;
    private BigDecimal saldoPendiente;
    private List<CobroDomain> cobros;
    private EstadoCuenta estado;
    private LocalDate fechaEmision;
    private LocalDate fechaVencimiento;

    public CuentaCobrarDomain() {
        super();
    }

    public CuentaCobrarDomain(UUID id, String numeroCuenta, VentaDomain venta, UsuarioDomain cliente, BigDecimal montoTotal, BigDecimal saldoPendiente, List<CobroDomain> cobros, EstadoCuenta estado, LocalDate fechaEmision, LocalDate fechaVencimiento) {
        super(id);
        setNumeroCuenta(numeroCuenta);
        setVenta(venta);
        setCliente(cliente);
        setMontoTotal(montoTotal);
        setSaldoPendiente(saldoPendiente);
        setCobros(cobros);
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

    public VentaDomain getVenta() {
        return venta;
    }

    public void setVenta(VentaDomain venta) {
        this.venta = venta;
    }

    public UsuarioDomain getCliente() {
        return cliente;
    }

    public void setCliente(UsuarioDomain cliente) {
        this.cliente = cliente;
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

    public List<CobroDomain> getCobros() {
        return cobros;
    }

    public void setCobros(List<CobroDomain> cobros) {
        this.cobros = cobros;
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
