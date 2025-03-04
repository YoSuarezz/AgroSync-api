package com.sedikev.application.domain;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaDomain {
    private Long id;
    private String idAnimal;
    private Integer idComprador;
    private String estado;
    private Float precioKilo;
    private Date fecha;
}
