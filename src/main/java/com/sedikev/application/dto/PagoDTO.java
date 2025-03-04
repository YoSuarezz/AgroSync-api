package com.sedikev.application.dto;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoDTO {
    private Long id;
    private Long idVenta;
    private Integer idComprador;
    private String tipoPago;
    private Float cantidad;
    private String descripcion;
    private Date fecha;
}
