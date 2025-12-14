package com.agrosync.application.usecase.compras.rulesvalidator.impl;

import com.agrosync.application.primaryports.dto.compras.request.CompraIdSuscripcionDTO;
import com.agrosync.application.usecase.compras.rulesvalidator.ObtenerCompraPorIdRulesValidator;
import com.agrosync.domain.compras.rules.IdentificadorCompraExisteRule;
import com.agrosync.domain.suscripcion.rules.SuscripcionExisteRule;
import org.springframework.stereotype.Service;

@Service
public class ObtenerCompraPorIdRulesValidatorImpl implements ObtenerCompraPorIdRulesValidator {

    private final SuscripcionExisteRule suscripcionExisteRule;
    private final IdentificadorCompraExisteRule identificadorCompraExisteRule;

    public ObtenerCompraPorIdRulesValidatorImpl(SuscripcionExisteRule suscripcionExisteRule, IdentificadorCompraExisteRule identificadorCompraExisteRule) {
        this.suscripcionExisteRule = suscripcionExisteRule;
        this.identificadorCompraExisteRule = identificadorCompraExisteRule;
    }

    @Override
    public void validar(CompraIdSuscripcionDTO data) {
        suscripcionExisteRule.validate(data.getSuscripcionId());
        identificadorCompraExisteRule.validate(data);
    }
}
