package com.agrosync.domain.model;

import com.agrosync.domain.usuarios.UsuarioDomain;
import lombok.*;

import java.math.BigDecimal;

@Data
@Getter
@Setter
public class CarteraDomain {

    private Long id;
    private UsuarioDomain usuario;
    private BigDecimal saldo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UsuarioDomain getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDomain usuario) {
        this.usuario = usuario;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
}
