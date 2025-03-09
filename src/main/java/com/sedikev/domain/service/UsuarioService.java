package com.sedikev.domain.service;

import com.sedikev.application.domain.UsuarioDomain;
import com.sedikev.domain.entity.UsuarioEntity;

import java.util.List;

public interface UsuarioService {

    UsuarioDomain save(UsuarioDomain UsuarioDomain);

    UsuarioDomain findById(Long id);

    void deleteById(Long Id);

    List<UsuarioDomain> findAll();
}
