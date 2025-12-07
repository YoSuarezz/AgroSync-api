package com.agrosync.application.usecase.lotes.rulesvalidator.impl;

import com.agrosync.application.usecase.lotes.rulesvalidator.RegistrarNuevoLoteRulesValidator;
import com.agrosync.domain.lotes.LoteDomain;
import com.agrosync.domain.lotes.rules.AnimalesLoteMinimoRule;
import com.agrosync.domain.lotes.rules.ContramarcaNoVaciaRule;
import com.agrosync.domain.lotes.rules.ContramarcaSemanalUnicaRule;
import org.springframework.stereotype.Service;

@Service
public class RegistrarNuevoLoteRulesValidatorImpl implements RegistrarNuevoLoteRulesValidator {

    private final ContramarcaNoVaciaRule contramarcaNoVaciaRule;
    private final ContramarcaSemanalUnicaRule contramarcaSemanalUnicaRule;
    private final AnimalesLoteMinimoRule animalesLoteMinimoRule;

    public RegistrarNuevoLoteRulesValidatorImpl(ContramarcaNoVaciaRule contramarcaNoVaciaRule, ContramarcaSemanalUnicaRule contramarcaSemanalUnicaRule, AnimalesLoteMinimoRule animalesLoteMinimoRule) {
        this.contramarcaNoVaciaRule = contramarcaNoVaciaRule;
        this.contramarcaSemanalUnicaRule = contramarcaSemanalUnicaRule;
        this.animalesLoteMinimoRule = animalesLoteMinimoRule;
    }

    @Override
    public void validar(LoteDomain data) {
        contramarcaNoVaciaRule.validate(data.getContramarca());
        contramarcaSemanalUnicaRule.validate(data);
        animalesLoteMinimoRule.validate(data.getAnimales());
    }
}
