package com.sedikev.application.domain;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoteDomain {
    private Long id;
    private Integer idProveedor;
    private Integer contramarca;
    private Float precioKilo;
    private Date fecha;
}
