package com.agrosync.application.usecase.usuarios.impl;

import com.agrosync.application.primaryports.dto.usuarios.request.UsuarioIdSuscripcionDTO;
import com.agrosync.application.secondaryports.entity.usuarios.UsuarioEntity;
import com.agrosync.application.secondaryports.mapper.usuarios.UsuarioEntityMapper;
import com.agrosync.application.secondaryports.repository.UsuarioRepository;
import com.agrosync.application.usecase.usuarios.ObtenerUsuarioPorId;
import com.agrosync.application.usecase.usuarios.rulesvalidator.ObtenerUsuarioPorIdRulesValidator;
import com.agrosync.domain.usuarios.UsuarioDomain;
import com.agrosync.domain.usuarios.exceptions.UsuarioIdNoExisteException;
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
    public UsuarioDomain ejecutar(UsuarioIdSuscripcionDTO data) {
        obtenerUsuarioPorIdRulesValidator.validar(data);
        Optional<UsuarioEntity> resultado = usuarioRepository.findByIdAndSuscripcion_Id(data.getId(), data.getSuscripcionId());
        return UsuarioEntityMapper.INSTANCE.toDomain(resultado.get());
    }
}
