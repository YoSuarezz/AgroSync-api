package com.agrosync.application.primaryports.dto.carteras;

import com.agrosync.application.primaryports.dto.usuarios.UsuarioDTO;
import com.agrosync.crosscutting.helpers.NumericHelper;
import com.agrosync.crosscutting.helpers.ObjectHelper;
import lombok.*;

import java.math.BigDecimal;

@Data
@Getter
@Setter
public class CarteraDTO {

    private Long id;
    private UsuarioDTO usuario;
    private BigDecimal saldo;

    public CarteraDTO() {
        setId(id);
        setUsuario(UsuarioDTO.create());
        setSaldo(BigDecimal.ZERO);
    }

    public Long getId() {
        return id;
    }

    public CarteraDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public CarteraDTO setUsuario(UsuarioDTO usuario) {
        this.usuario = ObjectHelper.getDefault(usuario, UsuarioDTO.create());
        return this;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public CarteraDTO setSaldo(BigDecimal saldo) {
        this.saldo = BigDecimal.ZERO;
        return this;
    }
}
