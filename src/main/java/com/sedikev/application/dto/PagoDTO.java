package com.sedikev.application.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoDTO {

    private Long id;
    private VentaDTO ventaDTO;
    private UsuarioDTO usuarioDTO;
    private String tipoPago;
    private BigDecimal cantidad;
    private String descripcion;
    private Date fecha;
}
