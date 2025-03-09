package com.sedikev.domain.model;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoDomain {

    private Long id;
    private VentaDomain ventaDomain;
    private UsuarioDomain usuarioDomain;
    private String tipo_pago;
    private Float cantidad;
    private String descripcion;
    private Date fecha;
}
