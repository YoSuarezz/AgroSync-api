package com.agrosync.application.primaryports.dto.carteras;

import com.agrosync.application.primaryports.dto.usuarios.UsuarioDTO;
import com.agrosync.crosscutting.helpers.NumericHelper;
import com.agrosync.crosscutting.helpers.ObjectHelper;
import lombok.*;

import java.math.BigDecimal;

public class CarteraDTO {

    private Long id;
    private UsuarioDTO usuario;
    private BigDecimal saldo;

    public CarteraDTO() {
        setId(id);
        setUsuario(UsuarioDTO.create());
        setSaldo(BigDecimal.ZERO);
    }

    public CarteraDTO(Long id, UsuarioDTO usuario, BigDecimal saldo) {
        setId(id);
        setUsuario(usuario);
        setSaldo(saldo);
    }

    public static CarteraDTO create(Long id, UsuarioDTO usuario, BigDecimal saldo) {
        return new CarteraDTO(id, usuario, saldo);
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
        this.saldo = saldo;
        return this;
    }
}
