package com.sedikev.application.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Getter
@Setter
public class LoteDTO {

    private Long id;
    private UsuarioDTO usuario;
    private Integer contramarca;
    private BigDecimal precio_kilo;
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

    public Integer getContramarca() {
        return contramarca;
    }

    public void setContramarca(Integer contramarca) {
        this.contramarca = contramarca;
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

    public List<AnimalDTO> getAnimales() {
        return animales;
    }

    public void setAnimales(List<AnimalDTO> animales) {
        this.animales = animales;
    }
}
