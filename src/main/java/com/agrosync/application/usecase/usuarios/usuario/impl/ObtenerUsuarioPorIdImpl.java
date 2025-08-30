package com.agrosync.application.usecase.usuarios.usuario.impl;

import com.agrosync.application.secondaryports.entity.usuarios.UsuarioEntity;
import com.agrosync.application.secondaryports.mapper.usuarios.UsuarioEntityMapper;
import com.agrosync.application.secondaryports.repository.UsuarioRepository;
import com.agrosync.application.usecase.usuarios.usuario.ObtenerUsuarioPorId;
import com.agrosync.application.usecase.usuarios.usuario.rulesvalidator.ObtenerUsuarioPorIdRulesValidator;
import com.agrosync.domain.usuarios.UsuarioDomain;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ObtenerUsuarioPorIdImpl implements ObtenerUsuarioPorId {

    private final UsuarioRepository usuarioRepository;
    private final ObtenerUsuarioPorIdRulesValidator obtenerUsuarioPorIdRulesValidator;

    public ObtenerUsuarioPorIdImpl(UsuarioRepository usuarioRepository, ObtenerUsuarioPorIdRulesValidator obtenerUsuarioPorIdRulesValidator) {
        this.usuarioRepository = usuarioRepository;
        this.obtenerUsuarioPorIdRulesValidator = obtenerUsuarioPorIdRulesValidator;
    }

    @Override
    public UsuarioDomain ejecutar(Long data) {
        obtenerUsuarioPorIdRulesValidator.validar(data);
        Optional<UsuarioEntity> resultado = usuarioRepository.findById(data);
        return UsuarioEntityMapper.INSTANCE.toDomain(resultado.get());
    }
}
