package com.sedikev.domain.repository;

import com.sedikev.domain.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {

    Usuario save(Usuario usuario);

    Optional<Usuario> findById(Long id);

    void deleteById(Long id);

    List<Usuario> findAll();
}
