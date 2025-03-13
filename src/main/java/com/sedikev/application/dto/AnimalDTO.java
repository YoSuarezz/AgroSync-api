package com.sedikev.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalDTO {

    private String id;
    @JsonIgnoreProperties(value = "usuario")
    private LoteDTO lote;
    private String nombre;
    private BigDecimal peso;
    private String sexo;
    private Integer num_lote;
}
