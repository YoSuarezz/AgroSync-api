package com.sedikev.application.domain;

import lombok.Data;

@Data
public class AnimalDomain {
    private String id;
    private Integer idLote;
    private String nombre;
    private Float peso;
    private String sexo;
    private Integer numLote;

    public boolean esMachote() {
        return "M".equals(sexo);
    }
}
