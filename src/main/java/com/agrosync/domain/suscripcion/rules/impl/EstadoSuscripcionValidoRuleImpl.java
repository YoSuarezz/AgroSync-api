package com.agrosync.domain.suscripcion.rules.impl;

import com.agrosync.domain.enums.suscripcion.EstadoSuscripcionEnum;
import com.agrosync.domain.suscripcion.exceptions.EstadoSuscripcionNoValidoException;
import com.agrosync.domain.suscripcion.rules.EstadoSuscripcionValidoRule;
import org.springframework.stereotype.Service;

@Service
public class EstadoSuscripcionValidoRuleImpl implements EstadoSuscripcionValidoRule {

    @Override
    public void validate(EstadoSuscripcionEnum data) {
        if (data == null) {
            throw EstadoSuscripcionNoValidoException.create();
        }
    }
}
