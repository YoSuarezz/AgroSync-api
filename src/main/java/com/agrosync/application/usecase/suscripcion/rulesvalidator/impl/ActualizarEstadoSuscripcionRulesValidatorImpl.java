package com.agrosync.application.usecase.suscripcion.rulesvalidator.impl;

import com.agrosync.application.usecase.suscripcion.rulesvalidator.ActualizarEstadoSuscripcionRulesValidator;
import com.agrosync.domain.suscripcion.SuscripcionDomain;
import com.agrosync.domain.suscripcion.rules.EstadoSuscripcionValidoRule;
import com.agrosync.domain.suscripcion.rules.SuscripcionExisteRule;
import com.agrosync.domain.suscripcion.exceptions.FechaUltimoPagoRequeridaException;
import com.agrosync.domain.enums.suscripcion.EstadoSuscripcionEnum;
import org.springframework.stereotype.Service;

@Service
public class ActualizarEstadoSuscripcionRulesValidatorImpl implements ActualizarEstadoSuscripcionRulesValidator {

    private final SuscripcionExisteRule suscripcionExisteRule;
    private final EstadoSuscripcionValidoRule estadoSuscripcionValidoRule;

    public ActualizarEstadoSuscripcionRulesValidatorImpl(SuscripcionExisteRule suscripcionExisteRule,
                                                         EstadoSuscripcionValidoRule estadoSuscripcionValidoRule) {
        this.suscripcionExisteRule = suscripcionExisteRule;
        this.estadoSuscripcionValidoRule = estadoSuscripcionValidoRule;
    }

    @Override
    public void validar(SuscripcionDomain data) {
        suscripcionExisteRule.validate(data.getId());
        estadoSuscripcionValidoRule.validate(data.getEstadoSuscripcion());

        if (data.getEstadoSuscripcion() == EstadoSuscripcionEnum.ACTIVA && data.getFechaUltimoPago() == null) {
            throw FechaUltimoPagoRequeridaException.create();
        }
    }
}
