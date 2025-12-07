package com.agrosync.application.usecase.compras.rulesvalidator.impl;

import com.agrosync.application.primaryports.dto.compras.request.CompraPageDTO;
import com.agrosync.application.usecase.compras.rulesvalidator.ObtenerComprasRulesValidator;
import com.agrosync.crosscutting.helpers.UUIDHelper;
import com.agrosync.domain.suscripcion.rules.SuscripcionExisteRule;
import com.agrosync.domain.usuarios.rules.UsuarioIdExisteRule;
import org.springframework.stereotype.Service;

@Service
public class ObtenerComprasRulesValidatorImpl implements ObtenerComprasRulesValidator {

    private final SuscripcionExisteRule suscripcionExisteRule;
    private final UsuarioIdExisteRule usuarioIdExisteRule;

    public ObtenerComprasRulesValidatorImpl(SuscripcionExisteRule suscripcionExisteRule, UsuarioIdExisteRule usuarioIdExisteRule) {
        this.suscripcionExisteRule = suscripcionExisteRule;
        this.usuarioIdExisteRule = usuarioIdExisteRule;
    }

    @Override
    public void validar(CompraPageDTO data) {
        suscripcionExisteRule.validate(data.getSuscripcionId());
        if (data.getProveedorId() != null && !UUIDHelper.isDefault(data.getProveedorId())) {
            usuarioIdExisteRule.validate(data.getProveedorId());
        }
    }
}
