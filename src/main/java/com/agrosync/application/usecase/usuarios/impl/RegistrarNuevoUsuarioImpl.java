package com.agrosync.application.usecase.usuarios.impl;

import com.agrosync.application.secondaryports.entity.usuarios.UsuarioEntity;
import com.agrosync.application.secondaryports.mapper.usuarios.UsuarioEntityMapper;
import com.agrosync.application.secondaryports.repository.UsuarioRepository;
import com.agrosync.application.usecase.usuarios.RegistrarNuevoUsuario;
import com.agrosync.application.usecase.usuarios.rulesvalidator.RegistrarNuevoUsuarioRulesValidator;
import com.agrosync.domain.usuarios.UsuarioDomain;
import org.springframework.stereotype.Service;

@Service
public class RegistrarNuevoUsuarioImpl implements RegistrarNuevoUsuario {

    private final UsuarioRepository usuarioRepository;
    private final RegistrarNuevoUsuarioRulesValidator registrarNuevoUsuarioRulesValidator;
    private final UsuarioEntityMapper usuarioEntityMapper;

    public RegistrarNuevoUsuarioImpl(UsuarioRepository usuarioRepository, RegistrarNuevoUsuarioRulesValidator registrarNuevoUsuarioRulesValidator, UsuarioEntityMapper usuarioEntityMapper) {
        this.usuarioRepository = usuarioRepository;
        this.registrarNuevoUsuarioRulesValidator = registrarNuevoUsuarioRulesValidator;
        this.usuarioEntityMapper = usuarioEntityMapper;
    }

    @Override
    public void ejecutar(UsuarioDomain data) {
        registrarNuevoUsuarioRulesValidator.validar(data);
        UsuarioEntity usuarioEntity = usuarioEntityMapper.toEntity(data);
        usuarioEntity.getCartera().setUsuario(usuarioEntity);
        usuarioEntity.getCartera().setSuscripcion(usuarioEntity.getSuscripcion());
        usuarioRepository.save(usuarioEntity);
    }
}
