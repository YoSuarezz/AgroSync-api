package com.sedikev.application.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@Getter
@Setter
public class CarteraDTO {

    private Long id;
    private UsuarioDTO usuario;
    private BigDecimal saldo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
}
