package com.sedikev.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GastoDomain {

    private Long id;
    private LoteDomain loteDomain;
    private UsuarioDomain usuarioDomain;
    private BigDecimal cantidad;
    private String descripcion;
    private Date fecha;
}
