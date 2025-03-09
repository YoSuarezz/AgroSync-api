package com.sedikev.domain.model;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoteDomain {

    private Long id;
    private UsuarioDomain usuarioDomain;
    private Integer contramarca;
    private Float precio_kilo;
    private Date fecha;
}
