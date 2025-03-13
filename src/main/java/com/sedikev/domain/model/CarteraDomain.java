package com.sedikev.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarteraDomain {

    private Long id;
    private UsuarioDomain usuario;
    private BigDecimal saldo;

}
