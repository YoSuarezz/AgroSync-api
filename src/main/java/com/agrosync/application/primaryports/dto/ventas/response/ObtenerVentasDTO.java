package com.agrosync.application.primaryports.dto.ventas.response;

import com.agrosync.application.primaryports.dto.usuarios.response.ObtenerUsuarioDTO;
import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.crosscutting.helpers.TextHelper;
import com.agrosync.crosscutting.helpers.UUIDHelper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class ObtenerVentasDTO {

    private UUID id;
    private String numeroVenta;
    private ObtenerUsuarioDTO cliente;
    private LocalDate fechaVenta;
    private BigDecimal precioTotalVenta;
    private UUID cuentaCobrar;

    public ObtenerVentasDTO() {
        setId(UUIDHelper.getDefault());
        setNumeroVenta(TextHelper.EMPTY);
        setCliente(ObtenerUsuarioDTO.create());
        setFechaVenta(null);
        setPrecioTotalVenta(BigDecimal.ZERO);
        setCuentaCobrar(UUIDHelper.getDefault());
    }

    public ObtenerVentasDTO(UUID id, String numeroVenta, ObtenerUsuarioDTO cliente, LocalDate fechaVenta, BigDecimal precioTotalVenta, UUID cuentaCobrar) {
        setId(id);
        setNumeroVenta(numeroVenta);
        setCliente(cliente);
        setFechaVenta(fechaVenta);
        setPrecioTotalVenta(precioTotalVenta);
        setCuentaCobrar(cuentaCobrar);
    }

    public static ObtenerVentasDTO create(UUID id, String numeroVenta, ObtenerUsuarioDTO cliente, LocalDate fechaVenta, BigDecimal precioTotalVenta, UUID cuentaCobrar) {
        return new ObtenerVentasDTO(id, numeroVenta, cliente, fechaVenta, precioTotalVenta, cuentaCobrar);
    }

    public static ObtenerVentasDTO create() {
        return new ObtenerVentasDTO();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNumeroVenta() {
        return numeroVenta;
    }

    public void setNumeroVenta(String numeroVenta) {
        this.numeroVenta = numeroVenta;
    }

    public ObtenerUsuarioDTO getCliente() {
        return cliente;
    }

    public void setCliente(ObtenerUsuarioDTO cliente) {
        this.cliente = ObjectHelper.getDefault(cliente, ObtenerUsuarioDTO.create());
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

    public UUID getCuentaCobrar() {
        return cuentaCobrar;
    }

    public void setCuentaCobrar(UUID cuentaCobrar) {
        this.cuentaCobrar = UUIDHelper.getDefault(cuentaCobrar, UUIDHelper.getDefault());
    }
}
