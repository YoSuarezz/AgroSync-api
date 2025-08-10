package com.agrosync.application.usecase.usuarios.rulesvalidator.impl;

import com.agrosync.application.usecase.usuarios.rulesvalidator.ObtenerUsuarioPorIdRulesValidator;
import com.agrosync.domain.usuarios.rules.UsuarioIdExisteRule;
import org.springframework.stereotype.Service;

@Service
public class ObtenerUsuarioPorIdRulesValidatorImpl implements ObtenerUsuarioPorIdRulesValidator {

    private final UsuarioIdExisteRule usuarioIdExisteRule;

    public ObtenerUsuarioPorIdRulesValidatorImpl(UsuarioIdExisteRule usuarioIdExisteRule) {
        this.usuarioIdExisteRule = usuarioIdExisteRule;
    }

    @Override
    public void validar(Long data) {
        usuarioIdExisteRule.validate(data);
    }
}
