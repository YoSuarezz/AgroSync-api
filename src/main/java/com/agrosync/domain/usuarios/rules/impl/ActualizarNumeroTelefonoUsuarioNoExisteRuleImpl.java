package com.agrosync.domain.usuarios.rules.impl;

import com.agrosync.application.secondaryports.repository.UsuarioRepository;
import com.agrosync.domain.usuarios.UsuarioDomain;
import com.agrosync.domain.usuarios.exceptions.NumeroTelefonoUsuarioExisteException;
import com.agrosync.domain.usuarios.rules.ActualizarNumeroTelefonoUsuarioNoExisteRule;
import org.springframework.stereotype.Service;

@Service
public class ActualizarNumeroTelefonoUsuarioNoExisteRuleImpl implements ActualizarNumeroTelefonoUsuarioNoExisteRule {

    private final UsuarioRepository usuarioRepository;

    public ActualizarNumeroTelefonoUsuarioNoExisteRuleImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void validate(UsuarioDomain data) {
        if (usuarioRepository.existsByTelefonoAndIdNotAndSuscripcion_Id(data.getTelefono(), data.getId(), data.getSuscripcionId())) {
            throw NumeroTelefonoUsuarioExisteException.create();
        }
    }
}
