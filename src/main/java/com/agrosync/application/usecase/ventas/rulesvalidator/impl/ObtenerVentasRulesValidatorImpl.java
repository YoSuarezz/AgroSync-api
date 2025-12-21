package com.agrosync.application.usecase.ventas.rulesvalidator.impl;

import com.agrosync.application.primaryports.dto.ventas.request.VentaPageDTO;
import com.agrosync.application.usecase.ventas.rulesvalidator.ObtenerVentasRulesValidator;
import com.agrosync.crosscutting.helpers.UUIDHelper;
import com.agrosync.domain.suscripcion.rules.SuscripcionExisteRule;
import com.agrosync.domain.usuarios.rules.UsuarioIdExisteRule;
import org.springframework.stereotype.Service;

@Service
public class ObtenerVentasRulesValidatorImpl implements ObtenerVentasRulesValidator {

    private final SuscripcionExisteRule suscripcionExisteRule;
    private final UsuarioIdExisteRule usuarioIdExisteRule;

    public ObtenerVentasRulesValidatorImpl(SuscripcionExisteRule suscripcionExisteRule, UsuarioIdExisteRule usuarioIdExisteRule) {
        this.suscripcionExisteRule = suscripcionExisteRule;
        this.usuarioIdExisteRule = usuarioIdExisteRule;
    }

    @Override
    public void validar(VentaPageDTO data) {
        suscripcionExisteRule.validate(data.getSuscripcionId());
        if (data.getClienteId() != null && !UUIDHelper.isDefault(data.getClienteId())) {
            usuarioIdExisteRule.validate(data.getClienteId());
        }
    }
}
