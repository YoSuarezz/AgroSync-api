package com.agrosync.application.usecase.compras.rulesvalidator.impl;

import com.agrosync.application.usecase.compras.rulesvalidator.RegistrarNuevaCompraRulesValidator;
import com.agrosync.domain.compras.CompraDomain;
import com.agrosync.domain.compras.rules.FechaCompraValidaRule;
import com.agrosync.domain.suscripcion.rules.SuscripcionExisteRule;
import com.agrosync.domain.usuarios.rules.UsuarioIdExisteRule;
import org.springframework.stereotype.Service;

@Service
public class RegistrarNuevaCompraRulesValidatorImpl implements RegistrarNuevaCompraRulesValidator {

    private final UsuarioIdExisteRule usuarioIdExisteRule;
    private final SuscripcionExisteRule suscripcionExisteRule;
    private final FechaCompraValidaRule fechaCompraValidaRule;

    public RegistrarNuevaCompraRulesValidatorImpl(UsuarioIdExisteRule usuarioIdExisteRule,
                                                   SuscripcionExisteRule suscripcionExisteRule,
                                                   FechaCompraValidaRule fechaCompraValidaRule) {
        this.usuarioIdExisteRule = usuarioIdExisteRule;
        this.suscripcionExisteRule = suscripcionExisteRule;
        this.fechaCompraValidaRule = fechaCompraValidaRule;
    }

    @Override
    public void validar(CompraDomain data) {
        suscripcionExisteRule.validate(data.getSuscripcionId());
        usuarioIdExisteRule.validate(data.getProveedor().getId());
        fechaCompraValidaRule.validate(data.getFechaCompra());
    }
}
