package com.agrosync.application.usecase.usuarios.rulesvalidator.impl;

import com.agrosync.application.primaryports.dto.usuarios.request.UsuarioIdSuscripcionDTO;
import com.agrosync.application.usecase.usuarios.rulesvalidator.ObtenerUsuarioPorIdRulesValidator;
import com.agrosync.domain.suscripcion.rules.SuscripcionExisteRule;
import com.agrosync.domain.usuarios.rules.UsuarioIdExisteRule;
import org.springframework.stereotype.Service;

@Service
public class ObtenerUsuarioPorIdRulesValidatorImpl implements ObtenerUsuarioPorIdRulesValidator {

    private final UsuarioIdExisteRule usuarioIdExisteRule;
    private final SuscripcionExisteRule suscripcionExisteRule;

    public ObtenerUsuarioPorIdRulesValidatorImpl(UsuarioIdExisteRule usuarioIdExisteRule, SuscripcionExisteRule suscripcionExisteRule) {
        this.usuarioIdExisteRule = usuarioIdExisteRule;
        this.suscripcionExisteRule = suscripcionExisteRule;
    }

    @Override
    public void validar(UsuarioIdSuscripcionDTO data) {
        suscripcionExisteRule.validate(data.getSuscripcionId());
        usuarioIdExisteRule.validate(data.getId());
    }
}
