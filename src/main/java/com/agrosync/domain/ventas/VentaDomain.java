package com.agrosync.domain.ventas;

import com.agrosync.domain.BaseDomain;
import com.agrosync.domain.animales.AnimalDomain;
import com.agrosync.domain.cuentascobrar.CuentaCobrarDomain;
import com.agrosync.domain.usuarios.UsuarioDomain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class VentaDomain extends BaseDomain {

    private String numeroVenta;
    private UsuarioDomain cliente;
    private LocalDate fechaVenta;
    private BigDecimal precioTotalVenta;
    private List<AnimalDomain> animales;
    private CuentaCobrarDomain cuentaCobrar;

    public VentaDomain() {
        super();
    }

    public VentaDomain(UUID id, String numeroVenta, UsuarioDomain cliente, LocalDate fechaVenta, BigDecimal precioTotalVenta, List<AnimalDomain> animales, CuentaCobrarDomain cuentaCobrar) {
        super(id);
        setNumeroVenta(numeroVenta);
        setCliente(cliente);
        setFechaVenta(fechaVenta);
        setPrecioTotalVenta(precioTotalVenta);
        setAnimales(animales);
        setCuentaCobrar(cuentaCobrar);
    }

    public String getNumeroVenta() {
        return numeroVenta;
    }

    public void setNumeroVenta(String numeroVenta) {
        this.numeroVenta = numeroVenta;
    }

    public UsuarioDomain getCliente() {
        return cliente;
    }

    public void setCliente(UsuarioDomain cliente) {
        this.cliente = cliente;
    }

    public LocalDate getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(LocalDate fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public BigDecimal getPrecioTotalVenta() {
        return precioTotalVenta;
    }

    public void setPrecioTotalVenta(BigDecimal precioTotalVenta) {
        this.precioTotalVenta = precioTotalVenta;
    }

    public List<AnimalDomain> getAnimales() {
        return animales;
    }

    public void setAnimales(List<AnimalDomain> animales) {
        this.animales = animales;
    }

    public CuentaCobrarDomain getCuentaCobrar() {
        return cuentaCobrar;
    }

    public void setCuentaCobrar(CuentaCobrarDomain cuentaCobrar) {
        this.cuentaCobrar = cuentaCobrar;
    }
}
