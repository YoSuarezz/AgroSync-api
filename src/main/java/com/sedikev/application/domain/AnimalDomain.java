package com.sedikev.application.domain;

import com.sedikev.domain.entity.LoteEntity;
import lombok.Data;

@Data
public class AnimalDomain {
    private String id;
    private LoteEntity loteEntity;
    private String nombre;
    private Float peso;
    private String sexo;
    private Integer numLote;

    public boolean esMachote() {
        return "M".equals(sexo);
    }
}
