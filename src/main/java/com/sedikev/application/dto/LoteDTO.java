package com.sedikev.application.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoteDTO {

    private Long id;
    private UsuarioDTO usuarioDTO;
    private Integer contramarca;
    private BigDecimal precio_kilo;
    private Date fecha;
}
