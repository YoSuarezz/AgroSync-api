package com.sedikev.application.dto;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaDTO {
    private Long id;
    private String idAnimal;
    private Integer idComprador;
    private String estado;
    private Float precioKilo;
    private Date fecha;
}

