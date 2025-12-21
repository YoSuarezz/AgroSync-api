package com.agrosync.application.usecase.compras.rulesvalidator.impl;

import com.agrosync.application.usecase.compras.rulesvalidator.RegistrarNuevaCompraRulesValidator;
import com.agrosync.domain.compras.CompraDomain;
import com.agrosync.domain.compras.rules.FechaCompraValidaRule;
import com.agrosync.domain.compras.rules.LoteConAnimalesRule;
import com.agrosync.domain.suscripcion.rules.SuscripcionExisteRule;
import com.agrosync.domain.usuarios.rules.UsuarioIdExisteRule;
import com.agrosync.domain.usuarios.rules.UsuarioProveedorValidoRule;
import org.springframework.stereotype.Service;

@Service
public class RegistrarNuevaCompraRulesValidatorImpl implements RegistrarNuevaCompraRulesValidator {

    private final UsuarioIdExisteRule usuarioIdExisteRule;
    private final UsuarioProveedorValidoRule usuarioProveedorValidoRule;
    private final SuscripcionExisteRule suscripcionExisteRule;
    private final FechaCompraValidaRule fechaCompraValidaRule;
    private final LoteConAnimalesRule loteConAnimalesRule;

    public RegistrarNuevaCompraRulesValidatorImpl(UsuarioIdExisteRule usuarioIdExisteRule,
                                                   UsuarioProveedorValidoRule usuarioProveedorValidoRule,
                                                   SuscripcionExisteRule suscripcionExisteRule,
                                                   FechaCompraValidaRule fechaCompraValidaRule,
                                                   LoteConAnimalesRule loteConAnimalesRule) {
        this.usuarioIdExisteRule = usuarioIdExisteRule;
        this.usuarioProveedorValidoRule = usuarioProveedorValidoRule;
        this.suscripcionExisteRule = suscripcionExisteRule;
        this.fechaCompraValidaRule = fechaCompraValidaRule;
        this.loteConAnimalesRule = loteConAnimalesRule;
    }

    @Override
    public void validar(CompraDomain data) {
        suscripcionExisteRule.validate(data.getSuscripcionId());
        usuarioIdExisteRule.validate(data.getProveedor().getId());
        usuarioProveedorValidoRule.validate(data.getProveedor().getId());
        fechaCompraValidaRule.validate(data.getFechaCompra());
        loteConAnimalesRule.validate(data.getLote());
    }
}
