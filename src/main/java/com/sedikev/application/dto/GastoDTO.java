package com.sedikev.application.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GastoDTO {

    private Long id;
    private LoteDTO loteDTO;
    private UsuarioDTO usuarioDTO;
    private BigDecimal cantidad;
    private String descripcion;
    private Date fecha;
}

