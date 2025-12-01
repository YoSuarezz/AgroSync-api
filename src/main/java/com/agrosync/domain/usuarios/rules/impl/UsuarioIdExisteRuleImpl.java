package com.agrosync.domain.usuarios.rules.impl;

import com.agrosync.application.secondaryports.repository.UsuarioRepository;
import com.agrosync.domain.usuarios.exceptions.UsuarioIdNoExisteException;
import com.agrosync.domain.usuarios.rules.UsuarioIdExisteRule;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UsuarioIdExisteRuleImpl implements UsuarioIdExisteRule {

    private final UsuarioRepository usuarioRepository;

    public UsuarioIdExisteRuleImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void validate(UUID data) {
        if (!usuarioRepository.existsById(data)) {
            throw UsuarioIdNoExisteException.create();
        }
    }
}
