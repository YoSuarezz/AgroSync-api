package com.sedikev.application.dto;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoteDTO {
    private Long id;
    private Integer idProveedor;
    private Integer contramarca;
    private Float precioKilo;
    private Date fecha;
}