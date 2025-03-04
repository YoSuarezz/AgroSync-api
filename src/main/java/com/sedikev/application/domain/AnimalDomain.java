package com.sedikev.application.domain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalDomain {
    private String id;
    private Integer idLote;
    private String nombre;
    private Float peso;
    private String sexo;
    private Integer numLote;
}
