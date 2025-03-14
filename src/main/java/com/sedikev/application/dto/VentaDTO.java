package com.sedikev.application.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
@Getter
@Setter
public class VentaDTO {

    private Long id;
    private AnimalDTO animal;
    private UsuarioDTO usuario;
    private String estado;
    private BigDecimal precio_kilo;
    private LocalDate fecha;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AnimalDTO getAnimal() {
        return animal;
    }

    public void setAnimal(AnimalDTO animal) {
        this.animal = animal;
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

    public BigDecimal getPrecio_kilo() {
        return precio_kilo;
    }

    public void setPrecio_kilo(BigDecimal precio_kilo) {
        this.precio_kilo = precio_kilo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}

