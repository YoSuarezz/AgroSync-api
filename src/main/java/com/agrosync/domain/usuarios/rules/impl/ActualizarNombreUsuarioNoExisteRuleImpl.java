package com.agrosync.domain.usuarios.rules.impl;

import com.agrosync.application.secondaryports.repository.UsuarioRepository;
import com.agrosync.domain.usuarios.UsuarioDomain;
import com.agrosync.domain.usuarios.exceptions.NombreUsuarioExisteException;
import com.agrosync.domain.usuarios.rules.ActualizarNombreUsuarioNoExisteRule;
import org.springframework.stereotype.Service;

@Service
public class ActualizarNombreUsuarioNoExisteRuleImpl implements ActualizarNombreUsuarioNoExisteRule {

    private final UsuarioRepository usuarioRepository;

    public ActualizarNombreUsuarioNoExisteRuleImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void validate(UsuarioDomain data) {
        if (usuarioRepository.existsByNombreAndIdNotAndSuscripcion_Id(data.getNombre(), data.getId(), data.getSuscripcionId())) {
            throw NombreUsuarioExisteException.create();
        }
    }
}

