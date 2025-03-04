package com.sedikev.application.domain;

import lombok.Data;

@Data
public class CarteraDomain {
    private Long id;
    private Integer idUsuario;
    private Float saldo;

    public boolean tieneSaldoSuficiente(Float cantidad) {
        return saldo >= cantidad;
    }
}
