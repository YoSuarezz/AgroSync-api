package com.agrosync.application.primaryports.dto.usuarios.request;

import org.springframework.data.domain.Pageable;

public class UsuarioRequest {

    private Pageable pageable;
    private RegiserNewUserDTO usuario;

    public UsuarioRequest() {
    }

    public UsuarioRequest(Pageable pageable, RegiserNewUserDTO usuario) {
        setPageable(pageable);
        setUsuario(usuario);
    }

    public static UsuarioRequest create(Pageable pageable, RegiserNewUserDTO usuario) {
        return new UsuarioRequest(pageable, usuario);
    }

    public static UsuarioRequest create() {
        return new UsuarioRequest();
    }

    public Pageable getPageable() {
        return pageable;
    }

    public UsuarioRequest setPageable(Pageable pageable) {
        this.pageable = pageable;
        return this;
    }

    public RegiserNewUserDTO getUsuario() {
        return usuario;
    }

    public UsuarioRequest setUsuario(RegiserNewUserDTO usuario) {
        this.usuario = usuario;
        return this;
    }
}
