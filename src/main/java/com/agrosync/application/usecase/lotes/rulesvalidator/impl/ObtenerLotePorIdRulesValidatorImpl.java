package com.agrosync.application.usecase.lotes.rulesvalidator.impl;

import com.agrosync.application.primaryports.dto.lotes.request.LoteIdSuscripcionDTO;
import com.agrosync.application.usecase.lotes.rulesvalidator.ObtenerLotePorIdRulesValidator;
import com.agrosync.domain.lotes.rules.IdentificadorLoteExisteRule;
import com.agrosync.domain.suscripcion.rules.SuscripcionExisteRule;
import org.springframework.stereotype.Service;

@Service
public class ObtenerLotePorIdRulesValidatorImpl implements ObtenerLotePorIdRulesValidator {

    private final IdentificadorLoteExisteRule identificadorLoteExisteRule;
    private final SuscripcionExisteRule suscripcionExisteRule;

    public ObtenerLotePorIdRulesValidatorImpl(IdentificadorLoteExisteRule identificadorLoteExisteRule,
                                              SuscripcionExisteRule suscripcionExisteRule) {
        this.identificadorLoteExisteRule = identificadorLoteExisteRule;
        this.suscripcionExisteRule = suscripcionExisteRule;
    }

    @Override
    public void validar(LoteIdSuscripcionDTO data) {
        suscripcionExisteRule.validate(data.getSuscripcionId());
        identificadorLoteExisteRule.validate(data);
    }
}
