package com.agrosync.application.primaryports.dto.usuarios;

import org.springframework.data.domain.Pageable;

public class UsuarioRequest {

    private Pageable pageable;
    private UsuarioDTO usuario;

    public UsuarioRequest() {
    }

    public UsuarioRequest(Pageable pageable, UsuarioDTO usuario) {
        setPageable(pageable);
        setUsuario(usuario);
    }

    public static UsuarioRequest create(Pageable pageable, UsuarioDTO usuario) {
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

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public UsuarioRequest setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
        return this;
    }
}
