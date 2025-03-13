package com.sedikev.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AnimalDomain {

    private String id;
    @JsonIgnoreProperties(value = "usuario")
    private LoteDomain lote;
    private String nombre;
    private BigDecimal peso;
    private String sexo;
    private Integer num_lote;
}
