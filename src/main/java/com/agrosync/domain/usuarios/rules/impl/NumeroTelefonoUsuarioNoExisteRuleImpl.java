package com.agrosync.domain.usuarios.rules.impl;

import com.agrosync.application.secondaryports.repository.UsuarioRepository;
import com.agrosync.domain.usuarios.UsuarioDomain;
import com.agrosync.domain.usuarios.exceptions.NumeroTelefonoUsuarioExisteException;
import com.agrosync.domain.usuarios.rules.NumeroTelefonoUsuarioNoExisteRule;
import org.springframework.stereotype.Service;

@Service
public class NumeroTelefonoUsuarioNoExisteRuleImpl implements NumeroTelefonoUsuarioNoExisteRule {

    private final UsuarioRepository usuarioRepository;

    public NumeroTelefonoUsuarioNoExisteRuleImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void validate(UsuarioDomain data) {
        if (usuarioRepository.existsByTelefonoAndSuscripcion_Id(data.getTelefono(), data.getSuscripcionId())) {
            throw NumeroTelefonoUsuarioExisteException.create();
        }
    }
}
