package com.agrosync.application.usecase.usuarios.impl;

import com.agrosync.application.secondaryports.entity.usuarios.UsuarioEntity;
import com.agrosync.application.secondaryports.mapper.usuarios.UsuarioEntityMapper;
import com.agrosync.application.secondaryports.repository.UsuarioRepository;
import com.agrosync.application.usecase.usuarios.ActualizarUsuario;
import com.agrosync.application.usecase.usuarios.rulesvalidator.ActualizarUsuarioRulesValidator;
import com.agrosync.domain.usuarios.UsuarioDomain;
import com.agrosync.domain.usuarios.exceptions.UsuarioIdNoExisteException;
import org.springframework.stereotype.Service;

@Service
public class ActualizarUsuarioImpl implements ActualizarUsuario {

    private final UsuarioRepository usuarioRepository;
    private final ActualizarUsuarioRulesValidator actualizarUsuarioRulesValidator;
    private final UsuarioEntityMapper usuarioEntityMapper;

    public ActualizarUsuarioImpl(UsuarioRepository usuarioRepository, ActualizarUsuarioRulesValidator actualizarUsuarioRulesValidator, UsuarioEntityMapper usuarioEntityMapper) {
        this.usuarioRepository = usuarioRepository;
        this.actualizarUsuarioRulesValidator = actualizarUsuarioRulesValidator;
        this.usuarioEntityMapper = usuarioEntityMapper;
    }

    @Override
    public void ejecutar(UsuarioDomain data) {
        actualizarUsuarioRulesValidator.validar(data);
        UsuarioEntity usuarioExistente = usuarioRepository.findByIdAndSuscripcion_Id(data.getId(), data.getSuscripcionId())
                .orElseThrow(UsuarioIdNoExisteException::create);

        UsuarioEntity usuarioEntity = usuarioEntityMapper.toEntity(data);
        usuarioEntity.setCartera(usuarioExistente.getCartera());
        usuarioRepository.save(usuarioEntity);
    }
}
