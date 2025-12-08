package com.agrosync.application.usecase.cuentascobrar.rulesvalidator.impl;

import com.agrosync.application.primaryports.dto.cuentascobrar.request.CuentaCobrarIdSuscripcionDTO;
import com.agrosync.application.usecase.cuentascobrar.rulesvalidator.ObtenerCuentaCobrarPorIdRulesValidator;
import com.agrosync.domain.cuentascobrar.rules.IdentificadorCuentaCobrarExisteRule;
import com.agrosync.domain.suscripcion.rules.SuscripcionExisteRule;
import org.springframework.stereotype.Service;

@Service
public class ObtenerCuentaCobrarPorIdRulesValidatorImpl implements ObtenerCuentaCobrarPorIdRulesValidator {

    private final IdentificadorCuentaCobrarExisteRule identificadorCuentaCobrarExisteRule;
    private final SuscripcionExisteRule suscripcionExisteRule;

    public ObtenerCuentaCobrarPorIdRulesValidatorImpl(IdentificadorCuentaCobrarExisteRule identificadorCuentaCobrarExisteRule, SuscripcionExisteRule suscripcionExisteRule) {
        this.identificadorCuentaCobrarExisteRule = identificadorCuentaCobrarExisteRule;
        this.suscripcionExisteRule = suscripcionExisteRule;
    }

    @Override
    public void validar(CuentaCobrarIdSuscripcionDTO data) {
        suscripcionExisteRule.validate(data.getSuscripcionId());
        identificadorCuentaCobrarExisteRule.validate(data.getId());
    }
}
