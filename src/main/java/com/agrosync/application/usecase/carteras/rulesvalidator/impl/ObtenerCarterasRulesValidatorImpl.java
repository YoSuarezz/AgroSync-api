package com.agrosync.application.usecase.carteras.rulesvalidator.impl;

import com.agrosync.application.primaryports.dto.carteras.request.CarteraPageDTO;
import com.agrosync.application.primaryports.dto.compras.request.CompraPageDTO;
import com.agrosync.application.usecase.carteras.rulesvalidator.ObtenerCarterasRulesValidator;
import com.agrosync.crosscutting.helpers.UUIDHelper;
import com.agrosync.domain.suscripcion.rules.SuscripcionExisteRule;
import com.agrosync.domain.usuarios.rules.UsuarioIdExisteRule;
import org.springframework.stereotype.Service;

@Service
public class ObtenerCarterasRulesValidatorImpl implements ObtenerCarterasRulesValidator {

    private final SuscripcionExisteRule suscripcionExisteRule;
    private final UsuarioIdExisteRule usuarioIdExisteRule;

    public ObtenerCarterasRulesValidatorImpl(SuscripcionExisteRule suscripcionExisteRule, UsuarioIdExisteRule usuarioIdExisteRule) {
        this.suscripcionExisteRule = suscripcionExisteRule;
        this.usuarioIdExisteRule = usuarioIdExisteRule;
    }

    @Override
    public void validar(CarteraPageDTO data) {
        suscripcionExisteRule.validate(data.getSuscripcionId());
        if (data.getUsuarioId() != null && !UUIDHelper.isDefault(data.getUsuarioId())) {
            usuarioIdExisteRule.validate(data.getUsuarioId());
        }
    }
}
