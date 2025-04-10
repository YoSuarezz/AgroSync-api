package com.sedikev.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Getter
@Setter
public class LoteDomain {

    private Long id;
    private UsuarioDomain usuario;
    private Integer contramarca;
    private BigDecimal precio_kilo;
    private LocalDate fecha;
    private List<AnimalDomain> animales;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UsuarioDomain getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDomain usuario) {
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

    public List<AnimalDomain> getAnimales() {
        return animales;
    }

    public void setAnimales(List<AnimalDomain> animales) {
        this.animales = animales;
    }
}
