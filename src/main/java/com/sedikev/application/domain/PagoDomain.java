package com.sedikev.application.domain;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoDomain {
    private Long id;
    private Long idVenta;
    private Integer idComprador;
    private String tipoPago;
    private Float cantidad;
    private String descripcion;
    private Date fecha;
}
