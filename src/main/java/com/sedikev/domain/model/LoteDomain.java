package com.sedikev.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoteDomain {

    private Long id;
    private UsuarioDomain usuario;
    private Integer contramarca;
    private BigDecimal precio_kilo;
    private LocalDate fecha;
}
