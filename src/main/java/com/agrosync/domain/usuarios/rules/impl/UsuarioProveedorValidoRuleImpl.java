package com.agrosync.domain.usuarios.rules.impl;

import com.agrosync.application.secondaryports.entity.usuarios.UsuarioEntity;
import com.agrosync.application.secondaryports.repository.UsuarioRepository;
import com.agrosync.domain.enums.usuarios.TipoUsuarioEnum;
import com.agrosync.domain.usuarios.exceptions.UsuarioNoEsProveedorException;
import com.agrosync.domain.usuarios.rules.UsuarioProveedorValidoRule;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UsuarioProveedorValidoRuleImpl implements UsuarioProveedorValidoRule {

    private final UsuarioRepository usuarioRepository;

    public UsuarioProveedorValidoRuleImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void validate(UUID usuarioId) {
        UsuarioEntity usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(UsuarioNoEsProveedorException::create);

        TipoUsuarioEnum tipo = usuario.getTipoUsuario();
        if (tipo != TipoUsuarioEnum.PROVEEDOR && tipo != TipoUsuarioEnum.AMBOS) {
            throw UsuarioNoEsProveedorException.create();
        }
    }
}

