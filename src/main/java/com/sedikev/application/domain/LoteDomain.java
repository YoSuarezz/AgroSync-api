package com.sedikev.application.domain;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoteDomain {

    private Long id;
    private UsuarioDomain usuarioDomainw;
    private Integer contramarca;
    private Float precio_kilo;
    private Date fecha;
}
