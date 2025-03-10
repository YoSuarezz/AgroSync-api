package com.sedikev.domain.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CarteraDomain {

    private Long id;
    private UsuarioDomain usuarioDomain;
    private BigDecimal saldo;

}
