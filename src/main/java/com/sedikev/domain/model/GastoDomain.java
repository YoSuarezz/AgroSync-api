package com.sedikev.domain.model;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GastoDomain {

    private Long id;
    private LoteDomain loteDomain;
    private UsuarioDomain usuarioDomain;
    private Float cantidad;
    private String descripcion;
    private Date fecha;
}
