package com.sedikev.application.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarteraDTO {

    private Long id;
    private UsuarioDTO usuario;
    private BigDecimal saldo;
}
