package com.sedikev.application.primaryports.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Getter @Setter
public class VentaDTO {
    private Long id;
    private UsuarioDTO usuario;
    private String estado;
    private BigDecimal precioVenta;
    private LocalDate fecha;
    private List<AnimalDTO> animales;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
    }

    public List<AnimalDTO> getAnimales() {
        return animales;
    }

    public void setAnimales(List<AnimalDTO> animales) {
        this.animales = animales;
    }
}

