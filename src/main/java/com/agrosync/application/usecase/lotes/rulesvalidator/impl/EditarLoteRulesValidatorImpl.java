package com.agrosync.application.usecase.lotes.rulesvalidator.impl;

import com.agrosync.application.usecase.lotes.rulesvalidator.EditarLoteRulesValidator;
import com.agrosync.domain.IdConSuscripcion;
import com.agrosync.domain.lotes.LoteDomain;
import com.agrosync.domain.lotes.rules.ContramarcaNoVaciaRule;
import com.agrosync.domain.lotes.rules.IdentificadorLoteExisteRule;
import com.agrosync.domain.lotes.rules.LoteEditableRule;
import org.springframework.stereotype.Service;

@Service
public class EditarLoteRulesValidatorImpl implements EditarLoteRulesValidator {

    private final IdentificadorLoteExisteRule identificadorLoteExisteRule;
    private final LoteEditableRule loteEditableRule;
    private final ContramarcaNoVaciaRule contramarcaNoVaciaRule;

    public EditarLoteRulesValidatorImpl(IdentificadorLoteExisteRule identificadorLoteExisteRule,
            LoteEditableRule loteEditableRule,
            ContramarcaNoVaciaRule contramarcaNoVaciaRule) {
        this.identificadorLoteExisteRule = identificadorLoteExisteRule;
        this.loteEditableRule = loteEditableRule;
        this.contramarcaNoVaciaRule = contramarcaNoVaciaRule;
    }

    @Override
    public void validar(LoteDomain data) {
        identificadorLoteExisteRule.validate(IdConSuscripcion.of(data.getId(), data.getSuscripcionId()));
        loteEditableRule.validate(data);
        contramarcaNoVaciaRule.validate(data.getContramarca());
    }
}
