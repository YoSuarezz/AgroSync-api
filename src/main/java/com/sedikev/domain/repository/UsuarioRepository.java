package com.sedikev.domain.repository;

import com.sedikev.infrastructure.adapter.entity.UsuarioEntity;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {

    UsuarioEntity save(UsuarioEntity usuarioEntity);

    Optional<UsuarioEntity> findById(Long id);

    void deleteById(Long id);

    List<UsuarioEntity> findAll();
}
