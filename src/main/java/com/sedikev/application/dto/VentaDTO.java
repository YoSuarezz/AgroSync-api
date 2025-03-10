package com.sedikev.application.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaDTO {

    private Long id;
    private AnimalDTO animalDTO;
    private UsuarioDTO usuarioDTO;
    private String estado;
    private BigDecimal precio_kilo;
    private Date fecha;
}

