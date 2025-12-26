package com.agrosync.application.usecase.suscripcion.rulesvalidator;

import com.agrosync.domain.suscripcion.SuscripcionDomain;

public interface ActualizarPlanSuscripcionRulesValidator {
    void validar(SuscripcionDomain data);
}
