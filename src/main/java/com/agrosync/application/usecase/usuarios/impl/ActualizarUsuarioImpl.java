package com.agrosync.application.usecase.usuarios.impl;

import com.agrosync.application.secondaryports.entity.usuarios.UsuarioEntity;
import com.agrosync.application.secondaryports.mapper.usuarios.UsuarioEntityMapper;
import com.agrosync.application.secondaryports.repository.UsuarioRepository;
import com.agrosync.application.usecase.usuarios.ActualizarUsuario;
import com.agrosync.application.usecase.usuarios.rulesvalidator.ActualizarUsuarioRulesValidator;
import com.agrosync.domain.usuarios.UsuarioDomain;
import org.springframework.stereotype.Service;

@Service
public class ActualizarUsuarioImpl implements ActualizarUsuario {

    private final UsuarioRepository usuarioRepository;
    private final ActualizarUsuarioRulesValidator actualizarUsuarioRulesValidator;

    public ActualizarUsuarioImpl(UsuarioRepository usuarioRepository, ActualizarUsuarioRulesValidator actualizarUsuarioRulesValidator) {
        this.usuarioRepository = usuarioRepository;
        this.actualizarUsuarioRulesValidator = actualizarUsuarioRulesValidator;
    }

    @Override
    public void ejecutar(UsuarioDomain data) {
        actualizarUsuarioRulesValidator.validar(data);
        UsuarioEntity usuarioEntity = UsuarioEntityMapper.INSTANCE.toEntity(data);
        usuarioRepository.save(usuarioEntity);
    }
}
