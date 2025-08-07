package com.sedikev.domain.service;

import com.sedikev.domain.model.UsuarioDomain;

import java.util.List;

public interface UsuarioService {

    UsuarioDomain save(UsuarioDomain UsuarioDomain);

    UsuarioDomain update(UsuarioDomain UsuarioDomain);

    UsuarioDomain findById(Long id);

    void deleteById(Long Id);

    List<UsuarioDomain> findAll();
}
