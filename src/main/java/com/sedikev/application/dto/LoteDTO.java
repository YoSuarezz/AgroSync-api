package com.sedikev.application.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoteDTO {

    private Long id;
    private UsuarioDTO usuario;
    private Integer contramarca;
    private BigDecimal precio_kilo;
    private LocalDate fecha;
}
