package com.agrosync.application.usecase.cobros.rulesvalidator.impl;

import com.agrosync.application.primaryports.dto.cobros.request.CobroIdSuscripcionDTO;
import com.agrosync.application.usecase.cobros.rulesvalidator.ObtenerCobroPorIdRulesValidator;
import com.agrosync.domain.IdConSuscripcion;
import com.agrosync.domain.cobros.rules.CobroExisteRule;
import com.agrosync.domain.suscripcion.rules.SuscripcionExisteRule;
import org.springframework.stereotype.Service;

@Service
public class ObtenerCobroPorIdRulesValidatorImpl implements ObtenerCobroPorIdRulesValidator {

    private final CobroExisteRule cobroExisteRule;
    private final SuscripcionExisteRule suscripcionExisteRule;

    public ObtenerCobroPorIdRulesValidatorImpl(CobroExisteRule cobroExisteRule,
                                                SuscripcionExisteRule suscripcionExisteRule) {
        this.cobroExisteRule = cobroExisteRule;
        this.suscripcionExisteRule = suscripcionExisteRule;
    }

    @Override
    public void validar(CobroIdSuscripcionDTO data) {
        suscripcionExisteRule.validate(data.getSuscripcionId());
        cobroExisteRule.validate(IdConSuscripcion.of(data.getId(), data.getSuscripcionId()));
    }
}

