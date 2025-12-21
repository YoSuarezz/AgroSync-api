package com.agrosync.application.usecase.ventas.rulesvalidator.impl;

import com.agrosync.application.primaryports.dto.ventas.request.VentaIdSuscripcionDTO;
import com.agrosync.application.usecase.ventas.rulesvalidator.ObtenerVentaPorIdRulesValidator;
import com.agrosync.domain.IdConSuscripcion;
import com.agrosync.domain.suscripcion.rules.SuscripcionExisteRule;
import com.agrosync.domain.ventas.rules.IdentificadorVentaExisteRule;
import org.springframework.stereotype.Service;

@Service
public class ObtenerVentaPorIdRulesValidatorImpl implements ObtenerVentaPorIdRulesValidator {

    private final SuscripcionExisteRule suscripcionExisteRule;
    private final IdentificadorVentaExisteRule identificadorVentaExisteRule;

    public ObtenerVentaPorIdRulesValidatorImpl(SuscripcionExisteRule suscripcionExisteRule, IdentificadorVentaExisteRule identificadorVentaExisteRule) {
        this.suscripcionExisteRule = suscripcionExisteRule;
        this.identificadorVentaExisteRule = identificadorVentaExisteRule;
    }

    @Override
    public void validar(VentaIdSuscripcionDTO data) {
        suscripcionExisteRule.validate(data.getSuscripcionId());
        identificadorVentaExisteRule.validate(IdConSuscripcion.of(data.getId(), data.getSuscripcionId()));
    }
}
