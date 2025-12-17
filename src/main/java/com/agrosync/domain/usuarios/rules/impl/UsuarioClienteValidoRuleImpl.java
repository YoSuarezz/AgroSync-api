package com.agrosync.domain.usuarios.rules.impl;

import com.agrosync.application.secondaryports.entity.usuarios.UsuarioEntity;
import com.agrosync.application.secondaryports.repository.UsuarioRepository;
import com.agrosync.domain.enums.usuarios.TipoUsuarioEnum;
import com.agrosync.domain.usuarios.exceptions.UsuarioNoEsClienteException;
import com.agrosync.domain.usuarios.rules.UsuarioClienteValidoRule;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UsuarioClienteValidoRuleImpl implements UsuarioClienteValidoRule {

    private final UsuarioRepository usuarioRepository;

    public UsuarioClienteValidoRuleImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void validate(UUID usuarioId) {
        UsuarioEntity usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(UsuarioNoEsClienteException::create);

        TipoUsuarioEnum tipo = usuario.getTipoUsuario();
        if (tipo != TipoUsuarioEnum.CLIENTE && tipo != TipoUsuarioEnum.AMBOS) {
            throw UsuarioNoEsClienteException.create();
        }
    }
}

