package com.sedikev.application.usecase.usuarios.impl;

import com.sedikev.application.secondaryports.entity.usuarios.UsuarioEntity;
import com.sedikev.application.secondaryports.mapper.usuarios.UsuarioEntityMapper;
import com.sedikev.application.secondaryports.repository.UsuarioRepository;
import com.sedikev.application.usecase.usuarios.RegistrarNuevoUsuario;
import com.sedikev.domain.usuarios.UsuarioDomain;
import org.springframework.stereotype.Service;

@Service
public class RegistrarNuevoUsuarioImpl implements RegistrarNuevoUsuario {

    private final UsuarioRepository usuarioRepository;

    public RegistrarNuevoUsuarioImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void ejecutar(UsuarioDomain data) {
        UsuarioEntity usuarioEntity = UsuarioEntityMapper.INSTANCE.toEntity(data);
        usuarioRepository.save(usuarioEntity);
    }
}
