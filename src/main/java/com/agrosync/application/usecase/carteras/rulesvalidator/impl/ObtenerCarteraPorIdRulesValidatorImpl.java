package com.agrosync.application.usecase.carteras.rulesvalidator.impl;

import com.agrosync.application.primaryports.dto.carteras.request.CarteraIdSuscripcionDTO;
import com.agrosync.application.primaryports.dto.usuarios.request.UsuarioIdSuscripcionDTO;
import com.agrosync.application.usecase.carteras.rulesvalidator.ObtenerCarteraPorIdRulesValidator;
import com.agrosync.domain.carteras.rules.CarteraIdExisteRule;
import com.agrosync.domain.suscripcion.rules.SuscripcionExisteRule;
import org.springframework.stereotype.Service;

@Service
public class ObtenerCarteraPorIdRulesValidatorImpl implements ObtenerCarteraPorIdRulesValidator {

    private final CarteraIdExisteRule carteraIdExisteRule;
    private final SuscripcionExisteRule suscripcionExisteRule;

    public ObtenerCarteraPorIdRulesValidatorImpl(CarteraIdExisteRule carteraIdExisteRule, SuscripcionExisteRule suscripcionExisteRule) {
        this.carteraIdExisteRule = carteraIdExisteRule;
        this.suscripcionExisteRule = suscripcionExisteRule;
    }

    @Override
    public void validar(CarteraIdSuscripcionDTO data) {
        suscripcionExisteRule.validate(data.getSuscripcionId());
        carteraIdExisteRule.validate(data.getId());
    }
}
