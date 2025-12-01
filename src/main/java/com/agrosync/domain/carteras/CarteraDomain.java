package com.agrosync.domain.carteras;

import com.agrosync.domain.BaseDomain;
import com.agrosync.domain.usuarios.UsuarioDomain;

import java.math.BigDecimal;
import java.util.UUID;

public class CarteraDomain extends BaseDomain {

    private UsuarioDomain usuario;
    private BigDecimal saldoActual;
    private BigDecimal totalCuentasPagar;
    private BigDecimal totalCuentasCobrar;

    public CarteraDomain() {
        super();
    }

    public CarteraDomain(UUID id, UsuarioDomain usuario, BigDecimal saldoActual, BigDecimal totalCuentasPagar, BigDecimal totalCuentasCobrar) {
        super(id);
        setUsuario(usuario);
        setSaldoActual(saldoActual);
        setTotalCuentasPagar(totalCuentasPagar);
        setTotalCuentasCobrar(totalCuentasCobrar);
    }

    public UsuarioDomain getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDomain usuario) {
        this.usuario = usuario;
    }

    public BigDecimal getSaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(BigDecimal saldoActual) {
        this.saldoActual = saldoActual;
    }

    public BigDecimal getTotalCuentasPagar() {
        return totalCuentasPagar;
    }

    public void setTotalCuentasPagar(BigDecimal totalCuentasPagar) {
        this.totalCuentasPagar = totalCuentasPagar;
    }

    public BigDecimal getTotalCuentasCobrar() {
        return totalCuentasCobrar;
    }

    public void setTotalCuentasCobrar(BigDecimal totalCuentasCobrar) {
        this.totalCuentasCobrar = totalCuentasCobrar;
    }
}
