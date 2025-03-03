package com.sedikev.domain.service;

import com.sedikev.domain.entity.Usuario;

import java.util.List;

public interface UsuarioService {

    Usuario save(Usuario usuario);

    Usuario findById(Long id);

    void deleteById(Long Id);

    List<Usuario> findAll();
}
