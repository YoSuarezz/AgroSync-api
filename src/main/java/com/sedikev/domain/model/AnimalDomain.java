package com.sedikev.domain.model;

import lombok.Data;

@Data
public class AnimalDomain {

    private String id;
    private LoteDomain loteDomain;
    private String nombre;
    private Float peso;
    private String sexo;
    private Integer numLote;

    public boolean esMachote() {
        return "M".equals(sexo);
    }
}
