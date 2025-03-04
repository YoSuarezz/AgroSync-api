package com.sedikev.application.dto;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GastoDTO {
    private Long id;
    private Integer idLote;
    private Integer idProveedor;
    private Float cantidad;
    private String descripcion;
    private Date fecha;
}

