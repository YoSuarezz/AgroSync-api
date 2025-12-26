package com.agrosync.application.usecase.suscripcion.rulesvalidator.impl;

import com.agrosync.application.usecase.suscripcion.rulesvalidator.RegistrarSuscripcionRulesValidator;
import com.agrosync.domain.suscripcion.SuscripcionDomain;
import com.agrosync.domain.suscripcion.rules.DatosSuscripcionObligatoriosRule;
import com.agrosync.domain.suscripcion.rules.EmailSuscripcionNoExisteRule;
import com.agrosync.domain.suscripcion.rules.NitSuscripcionNoExisteRule;
import org.springframework.stereotype.Service;

@Service
public class RegistrarSuscripcionRulesValidatorImpl implements RegistrarSuscripcionRulesValidator {

    private final DatosSuscripcionObligatoriosRule datosSuscripcionObligatoriosRule;
    private final NitSuscripcionNoExisteRule nitSuscripcionNoExisteRule;
    private final EmailSuscripcionNoExisteRule emailSuscripcionNoExisteRule;

    public RegistrarSuscripcionRulesValidatorImpl(DatosSuscripcionObligatoriosRule datosSuscripcionObligatoriosRule,
                                                 NitSuscripcionNoExisteRule nitSuscripcionNoExisteRule,
                                                 EmailSuscripcionNoExisteRule emailSuscripcionNoExisteRule) {
        this.datosSuscripcionObligatoriosRule = datosSuscripcionObligatoriosRule;
        this.nitSuscripcionNoExisteRule = nitSuscripcionNoExisteRule;
        this.emailSuscripcionNoExisteRule = emailSuscripcionNoExisteRule;
    }

    @Override
    public void validar(SuscripcionDomain data) {
        datosSuscripcionObligatoriosRule.validate(data);
        nitSuscripcionNoExisteRule.validate(data.getNit());
        emailSuscripcionNoExisteRule.validate(data.getEmail());
    }
}
