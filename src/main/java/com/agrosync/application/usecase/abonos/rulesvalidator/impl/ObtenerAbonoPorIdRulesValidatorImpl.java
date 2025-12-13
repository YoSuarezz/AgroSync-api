package com.agrosync.application.usecase.abonos.rulesvalidator.impl;

import com.agrosync.application.primaryports.dto.abonos.request.AbonoIdSuscripcionDTO;
import com.agrosync.application.usecase.abonos.rulesvalidator.ObtenerAbonoPorIdRulesValidator;
import com.agrosync.domain.abonos.rules.AbonoExisteRule;
import com.agrosync.domain.suscripcion.rules.SuscripcionExisteRule;
import org.springframework.stereotype.Service;

@Service
public class ObtenerAbonoPorIdRulesValidatorImpl implements ObtenerAbonoPorIdRulesValidator {

    private final AbonoExisteRule abonoExisteRule;
    private final SuscripcionExisteRule suscripcionExisteRule;

    public ObtenerAbonoPorIdRulesValidatorImpl(AbonoExisteRule abonoExisteRule,
                                                SuscripcionExisteRule suscripcionExisteRule) {
        this.abonoExisteRule = abonoExisteRule;
        this.suscripcionExisteRule = suscripcionExisteRule;
    }

    @Override
    public void validar(AbonoIdSuscripcionDTO data) {
        suscripcionExisteRule.validate(data.getSuscripcionId());
        abonoExisteRule.validate(data);
    }
}

