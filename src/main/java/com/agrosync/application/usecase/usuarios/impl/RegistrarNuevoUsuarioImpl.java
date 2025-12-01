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

    public RegistrarNuevoUsuarioImpl(UsuarioRepository usuarioRepository, RegistrarNuevoUsuarioRulesValidator registrarNuevoUsuarioRulesValidator) {
        this.usuarioRepository = usuarioRepository;
        this.registrarNuevoUsuarioRulesValidator = registrarNuevoUsuarioRulesValidator;
    }

    @Override
    public void ejecutar(UsuarioDomain data) {
        registrarNuevoUsuarioRulesValidator.validar(data);
        UsuarioEntity usuarioEntity = UsuarioEntityMapper.INSTANCE.toEntity(data);
        usuarioEntity.getCartera().setUsuario(usuarioEntity);
        usuarioRepository.save(usuarioEntity);
    }
}
