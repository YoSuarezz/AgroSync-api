package com.sedikev.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaDomain {

    private Long id;
    private AnimalDomain animalDomain;
    private UsuarioDomain usuarioDomain;
    private String estado;
    private BigDecimal precio_kilo;
    private Date fecha;
}
