package com.agrosync.application.usecase.animales.rulesvalidator.impl;

import com.agrosync.application.usecase.animales.rulesvalidator.ReportarMuerteAnimalRulesValidator;
import com.agrosync.domain.IdConSuscripcion;
import com.agrosync.domain.animales.AnimalDomain;
import com.agrosync.domain.animales.rules.AnimalDisponibleRule;
import com.agrosync.domain.animales.rules.IdentificadorAnimalExisteRule;
import com.agrosync.domain.suscripcion.rules.SuscripcionExisteRule;
import org.springframework.stereotype.Service;

@Service
public class ReportarMuerteAnimalRulesValidatorImpl implements ReportarMuerteAnimalRulesValidator {

    private final SuscripcionExisteRule suscripcionExisteRule;
    private final IdentificadorAnimalExisteRule identificadorAnimalExisteRule;
    private final AnimalDisponibleRule animalDisponibleRule;

    public ReportarMuerteAnimalRulesValidatorImpl(SuscripcionExisteRule suscripcionExisteRule,
                                                  IdentificadorAnimalExisteRule identificadorAnimalExisteRule,
                                                  AnimalDisponibleRule animalDisponibleRule) {
        this.suscripcionExisteRule = suscripcionExisteRule;
        this.identificadorAnimalExisteRule = identificadorAnimalExisteRule;
        this.animalDisponibleRule = animalDisponibleRule;
    }

    @Override
    public void validar(AnimalDomain data) {
        suscripcionExisteRule.validate(data.getSuscripcionId());
        identificadorAnimalExisteRule.validate(IdConSuscripcion.of(data.getId(), data.getSuscripcionId()));
        animalDisponibleRule.validate(data);
    }
}
