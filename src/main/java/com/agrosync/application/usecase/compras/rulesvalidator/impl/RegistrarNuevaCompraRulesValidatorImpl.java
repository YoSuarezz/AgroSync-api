package com.agrosync.application.usecase.compras.rulesvalidator.impl;

import com.agrosync.application.usecase.compras.rulesvalidator.RegistrarNuevaCompraRulesValidator;
import com.agrosync.domain.compras.CompraDomain;
import com.agrosync.domain.suscripcion.rules.SuscripcionExisteRule;
import com.agrosync.domain.usuarios.rules.UsuarioIdExisteRule;
import org.springframework.stereotype.Service;

@Service
public class RegistrarNuevaCompraRulesValidatorImpl implements RegistrarNuevaCompraRulesValidator {

    private final UsuarioIdExisteRule usuarioIdExisteRule;
    private final SuscripcionExisteRule suscripcionExisteRule;

    public RegistrarNuevaCompraRulesValidatorImpl(UsuarioIdExisteRule usuarioIdExisteRule, SuscripcionExisteRule suscripcionExisteRule) {
        this.usuarioIdExisteRule = usuarioIdExisteRule;
        this.suscripcionExisteRule = suscripcionExisteRule;
    }

    @Override
    public void validar(CompraDomain data) {
        usuarioIdExisteRule.validate(data.getProveedor().getId());
        suscripcionExisteRule.validate(data.getSuscripcionId());
    }
}
