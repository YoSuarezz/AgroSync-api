package com.sedikev.domain.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AnimalDomain {

    private String id;
    private Long id_lote;
    private String nombre;
    private BigDecimal peso;
    private String sexo;
    private Integer num_lote;
}
