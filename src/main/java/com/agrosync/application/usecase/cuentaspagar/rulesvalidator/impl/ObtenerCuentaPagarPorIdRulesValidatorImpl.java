package com.agrosync.application.usecase.cuentaspagar.rulesvalidator.impl;

import com.agrosync.application.primaryports.dto.cuentaspagar.request.CuentaPagarIdSuscripcionDTO;
import com.agrosync.application.usecase.cuentaspagar.rulesvalidator.ObtenerCuentaPagarPorIdRulesValidator;
import com.agrosync.domain.cuentaspagar.rules.IdentificadorCuentaPagarExisteRule;
import com.agrosync.domain.suscripcion.rules.SuscripcionExisteRule;
import org.springframework.stereotype.Service;

@Service
public class ObtenerCuentaPagarPorIdRulesValidatorImpl implements ObtenerCuentaPagarPorIdRulesValidator {

    private final IdentificadorCuentaPagarExisteRule identificadorCuentaPagarExisteRule;
    private final SuscripcionExisteRule suscripcionExisteRule;

    public ObtenerCuentaPagarPorIdRulesValidatorImpl(IdentificadorCuentaPagarExisteRule identificadorCuentaPagarExisteRule, SuscripcionExisteRule suscripcionExisteRule) {
        this.identificadorCuentaPagarExisteRule = identificadorCuentaPagarExisteRule;
        this.suscripcionExisteRule = suscripcionExisteRule;
    }

    @Override
    public void validar(CuentaPagarIdSuscripcionDTO data) {
        suscripcionExisteRule.validate(data.getSuscripcionId());
        identificadorCuentaPagarExisteRule.validate(data.getId());
    }
}
