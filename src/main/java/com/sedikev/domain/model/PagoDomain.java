package com.sedikev.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoDomain {

    private Long id;
    private VentaDomain ventaDomain;
    private UsuarioDomain usuarioDomain;
    private String tipo_pago;
    private BigDecimal cantidad;
    private String descripcion;
    private Date fecha;
}
