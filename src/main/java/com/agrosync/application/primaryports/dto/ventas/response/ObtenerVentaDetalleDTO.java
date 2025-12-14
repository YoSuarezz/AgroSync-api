package com.agrosync.application.primaryports.dto.ventas.response;

import com.agrosync.application.primaryports.dto.animales.response.ObtenerAnimalDTO;
import com.agrosync.application.primaryports.dto.cuentascobrar.response.ObtenerCuentaCobrarDTO;
import com.agrosync.application.primaryports.dto.usuarios.response.ObtenerUsuarioDTO;
import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.crosscutting.helpers.TextHelper;
import com.agrosync.crosscutting.helpers.UUIDHelper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ObtenerVentaDetalleDTO {

    private UUID id;
    private String numeroVenta;
    private ObtenerUsuarioDTO cliente;
    private LocalDate fechaVenta;
    private BigDecimal precioTotalVenta;
    private ObtenerCuentaCobrarDTO cuentaCobrar;
    private List<ObtenerAnimalDTO> animales;

    public ObtenerVentaDetalleDTO() {
        setId(UUIDHelper.getDefault());
        setNumeroVenta(TextHelper.EMPTY);
        setCliente(ObtenerUsuarioDTO.create());
        setFechaVenta(null);
        setPrecioTotalVenta(BigDecimal.ZERO);
        setCuentaCobrar(ObtenerCuentaCobrarDTO.create());
        setAnimales(new ArrayList<>());
    }

    public ObtenerVentaDetalleDTO(UUID id, String numeroVenta, ObtenerUsuarioDTO cliente, LocalDate fechaVenta, BigDecimal precioTotalVenta, ObtenerCuentaCobrarDTO cuentaCobrar, List<ObtenerAnimalDTO> animales) {
        setId(id);
        setNumeroVenta(numeroVenta);
        setCliente(cliente);
        setFechaVenta(fechaVenta);
        setPrecioTotalVenta(precioTotalVenta);
        setCuentaCobrar(cuentaCobrar);
        setAnimales(animales);
    }

    public static ObtenerVentaDetalleDTO create(UUID id, String numeroVenta, ObtenerUsuarioDTO cliente, LocalDate fechaVenta, BigDecimal precioTotalVenta, ObtenerCuentaCobrarDTO cuentaCobrar, List<ObtenerAnimalDTO> animales) {
        return new ObtenerVentaDetalleDTO(id, numeroVenta, cliente, fechaVenta, precioTotalVenta, cuentaCobrar, animales);
    }

    public static ObtenerVentaDetalleDTO create() {
        return new ObtenerVentaDetalleDTO();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = UUIDHelper.getDefault(id, UUIDHelper.getDefault());
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

    public ObtenerCuentaCobrarDTO getCuentaCobrar() {
        return cuentaCobrar;
    }

    public void setCuentaCobrar(ObtenerCuentaCobrarDTO cuentaCobrar) {
        this.cuentaCobrar = ObjectHelper.getDefault(cuentaCobrar, ObtenerCuentaCobrarDTO.create());
    }

    public List<ObtenerAnimalDTO> getAnimales() {
        return animales;
    }

    public void setAnimales(List<ObtenerAnimalDTO> animales) {
        this.animales = ObjectHelper.getDefault(animales, new ArrayList<>());
    }
}
