package com.sedikev.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaDomain {

    private Long id;
    private AnimalDomain animal;
    private UsuarioDomain usuario;
    private String estado;
    private BigDecimal precio_kilo;
    private LocalDate fecha;
}
