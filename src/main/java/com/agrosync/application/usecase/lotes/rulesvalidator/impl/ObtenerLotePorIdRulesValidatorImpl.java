package com.agrosync.application.usecase.lotes.rulesvalidator.impl;

import com.agrosync.application.primaryports.dto.lotes.request.LoteIdSuscripcionDTO;
import com.agrosync.application.usecase.lotes.rulesvalidator.ObtenerLotePorIdRulesValidator;
import com.agrosync.domain.animales.rules.IdentificadorAnimalExisteRule;
import com.agrosync.domain.suscripcion.rules.SuscripcionExisteRule;
import org.springframework.stereotype.Service;

@Service
public class ObtenerLotePorIdRulesValidatorImpl implements ObtenerLotePorIdRulesValidator {

    private final IdentificadorAnimalExisteRule identificadorAnimalExisteRule;
    private final SuscripcionExisteRule suscripcionExisteRule;

    public ObtenerLotePorIdRulesValidatorImpl(IdentificadorAnimalExisteRule identificadorAnimalExisteRule,
                                              SuscripcionExisteRule suscripcionExisteRule) {
        this.identificadorAnimalExisteRule = identificadorAnimalExisteRule;
        this.suscripcionExisteRule = suscripcionExisteRule;
    }

    @Override
    public void validar(LoteIdSuscripcionDTO data) {
        suscripcionExisteRule.validate(data.getSuscripcionId());
        identificadorAnimalExisteRule.validate(data.getId());
    }
}
