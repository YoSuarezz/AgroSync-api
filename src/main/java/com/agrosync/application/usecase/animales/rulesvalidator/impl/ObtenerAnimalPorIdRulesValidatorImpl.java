package com.agrosync.application.usecase.animales.rulesvalidator.impl;

import com.agrosync.application.primaryports.dto.animales.request.AnimalIdSuscripcionDTO;
import com.agrosync.application.usecase.animales.rulesvalidator.ObtenerAnimalPorIdRulesValidator;
import com.agrosync.domain.animales.rules.IdentificadorAnimalExisteRule;
import com.agrosync.domain.suscripcion.rules.SuscripcionExisteRule;
import org.springframework.stereotype.Service;

@Service
public class ObtenerAnimalPorIdRulesValidatorImpl implements ObtenerAnimalPorIdRulesValidator {

    private final IdentificadorAnimalExisteRule identificadorAnimalExisteRule;
    private final SuscripcionExisteRule suscripcionExisteRule;

    public ObtenerAnimalPorIdRulesValidatorImpl(IdentificadorAnimalExisteRule identificadorAnimalExisteRule, SuscripcionExisteRule suscripcionExisteRule) {
        this.identificadorAnimalExisteRule = identificadorAnimalExisteRule;
        this.suscripcionExisteRule = suscripcionExisteRule;
    }

    @Override
    public void validar(AnimalIdSuscripcionDTO data) {
        suscripcionExisteRule.validate(data.getSuscripcionId());
        identificadorAnimalExisteRule.validate(data.getId());
    }
}