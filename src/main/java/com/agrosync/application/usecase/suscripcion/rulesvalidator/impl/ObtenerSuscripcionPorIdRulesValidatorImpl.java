package com.agrosync.application.usecase.suscripcion.rulesvalidator.impl;

import com.agrosync.application.usecase.suscripcion.rulesvalidator.ObtenerSuscripcionPorIdRulesValidator;
import com.agrosync.domain.suscripcion.rules.SuscripcionExisteRule;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ObtenerSuscripcionPorIdRulesValidatorImpl implements ObtenerSuscripcionPorIdRulesValidator {

    private final SuscripcionExisteRule suscripcionExisteRule;

    public ObtenerSuscripcionPorIdRulesValidatorImpl(SuscripcionExisteRule suscripcionExisteRule) {
        this.suscripcionExisteRule = suscripcionExisteRule;
    }

    @Override
    public void validar(UUID data) {
        suscripcionExisteRule.validate(data);
    }
}
