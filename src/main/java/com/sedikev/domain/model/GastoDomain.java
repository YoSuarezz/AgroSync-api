package com.sedikev.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GastoDomain {

    private Long id;
    private LoteDomain lote;
    private UsuarioDomain usuario;
    private BigDecimal cantidad;
    private String descripcion;
    private LocalDate fecha;


}
