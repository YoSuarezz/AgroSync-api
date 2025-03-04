package com.sedikev.domain.service;

import com.sedikev.domain.entity.UsuarioEntity;

import java.util.List;

public interface UsuarioService {

    UsuarioEntity save(UsuarioEntity usuarioEntity);

    UsuarioEntity findById(Long id);

    void deleteById(Long Id);

    List<UsuarioEntity> findAll();
}
