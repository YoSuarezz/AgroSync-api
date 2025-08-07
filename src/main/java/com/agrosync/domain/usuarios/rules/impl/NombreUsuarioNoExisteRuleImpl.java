package com.agrosync.domain.usuarios.rules.impl;

import com.agrosync.application.secondaryports.repository.UsuarioRepository;
import com.agrosync.domain.usuarios.exceptions.NombreUsuarioExisteException;
import com.agrosync.domain.usuarios.rules.NombreUsuarioNoExisteRule;
import org.springframework.stereotype.Service;

@Service
public class NombreUsuarioNoExisteRuleImpl implements NombreUsuarioNoExisteRule {

    private final UsuarioRepository usuarioRepository;

    public NombreUsuarioNoExisteRuleImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void validate(String data) {
        if (usuarioRepository.existsByNombre(data)) {
            throw NombreUsuarioExisteException.create();
        }
    }
}
