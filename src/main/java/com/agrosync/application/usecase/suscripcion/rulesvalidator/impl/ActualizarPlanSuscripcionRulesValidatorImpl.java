package com.agrosync.application.usecase.suscripcion.rulesvalidator.impl;

import com.agrosync.application.usecase.suscripcion.rulesvalidator.ActualizarPlanSuscripcionRulesValidator;
import com.agrosync.domain.enums.suscripcion.PlanSuscripcionEnum;
import com.agrosync.domain.suscripcion.SuscripcionDomain;
import com.agrosync.domain.suscripcion.rules.SuscripcionExisteRule;
import org.springframework.stereotype.Service;

@Service
public class ActualizarPlanSuscripcionRulesValidatorImpl implements ActualizarPlanSuscripcionRulesValidator {

    private final SuscripcionExisteRule suscripcionExisteRule;

    public ActualizarPlanSuscripcionRulesValidatorImpl(SuscripcionExisteRule suscripcionExisteRule) {
        this.suscripcionExisteRule = suscripcionExisteRule;
    }

    @Override
    public void validar(SuscripcionDomain data) {
        suscripcionExisteRule.validate(data.getId());
        if (data.getPlanSuscripcion() == null) {
            throw new IllegalArgumentException("El plan de suscripción es obligatorio");
        }
        boolean planValido = data.getPlanSuscripcion() == PlanSuscripcionEnum.MENSUAL
                || data.getPlanSuscripcion() == PlanSuscripcionEnum.ANUAL;
        if (!planValido) {
            throw new IllegalArgumentException("El plan de suscripción no es válido");
        }
    }
}
