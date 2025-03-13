package com.sedikev.application.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaDTO {

    private Long id;
    private AnimalDTO animal;
    private UsuarioDTO usuario;
    private String estado;
    private BigDecimal precio_kilo;
    private LocalDate fecha;
}

