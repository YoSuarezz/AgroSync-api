package com.agrosync.domain.suscripcion.rules.impl;

import com.agrosync.crosscutting.helpers.TextHelper;
import com.agrosync.domain.suscripcion.SuscripcionDomain;
import com.agrosync.domain.suscripcion.exceptions.DatosSuscripcionObligatoriosException;
import com.agrosync.domain.suscripcion.rules.DatosSuscripcionObligatoriosRule;
import org.springframework.stereotype.Service;

@Service
public class DatosSuscripcionObligatoriosRuleImpl implements DatosSuscripcionObligatoriosRule {

    @Override
    public void validate(SuscripcionDomain data) {
        boolean faltanDatos = TextHelper.isEmpty(data.getNombreEmpresa()) ||
                TextHelper.isEmpty(data.getNit()) ||
                TextHelper.isEmpty(data.getEmail());

        if (faltanDatos) {
            throw DatosSuscripcionObligatoriosException.create();
        }
    }
}
