package com.agrosync.domain.service;

import com.agrosync.domain.usuarios.UsuarioDomain;

import java.util.List;

public interface UsuarioService {

    UsuarioDomain save(UsuarioDomain UsuarioDomain);

    UsuarioDomain update(UsuarioDomain UsuarioDomain);

    UsuarioDomain findById(Long id);

    void deleteById(Long Id);

    List<UsuarioDomain> findAll();
}
