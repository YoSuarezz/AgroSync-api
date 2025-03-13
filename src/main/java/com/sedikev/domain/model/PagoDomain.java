package com.sedikev.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
public class PagoDomain {

    private Long id;
    private VentaDomain venta;
    private UsuarioDomain usuario;
    private String tipo_pago;
    private BigDecimal cantidad;
    private String descripcion;
    private LocalDate fecha;
}
