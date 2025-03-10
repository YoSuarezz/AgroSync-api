package com.sedikev.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoteDomain {

    private Long id;
    private UsuarioDomain usuarioDomain;
    private Integer contramarca;
    private BigDecimal precio_kilo;
    private Date fecha;
}
