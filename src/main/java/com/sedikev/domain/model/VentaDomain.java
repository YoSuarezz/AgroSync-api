package com.sedikev.domain.model;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaDomain {

    private Long id;
    private AnimalDomain animalDomain;
    private UsuarioDomain usuarioDomain;
    private String estado;
    private Float precio_kilo;
    private Date fecha;
}
