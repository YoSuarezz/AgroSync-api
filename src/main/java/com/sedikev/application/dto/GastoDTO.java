package com.sedikev.application.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GastoDTO {

    private Long id;
    private LoteDTO lote;
    private UsuarioDTO usuario;
    private BigDecimal cantidad;
    private String descripcion;
    private LocalDate fecha;
}

