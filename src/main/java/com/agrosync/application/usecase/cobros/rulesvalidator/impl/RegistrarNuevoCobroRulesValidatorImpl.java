package com.agrosync.application.usecase.cobros.rulesvalidator.impl;

import com.agrosync.application.usecase.cobros.rulesvalidator.RegistrarNuevoCobroRulesValidator;
import com.agrosync.domain.cobros.CobroDomain;
import com.agrosync.domain.cobros.rules.CuentaCobrarDisponibleParaCobroRule;
import com.agrosync.domain.cobros.rules.MontoCobroMayorACeroRule;
import com.agrosync.domain.cobros.rules.MontoCobroNoExcedeSaldoRule;
import org.springframework.stereotype.Service;

@Service
public class RegistrarNuevoCobroRulesValidatorImpl implements RegistrarNuevoCobroRulesValidator {

    private final MontoCobroMayorACeroRule montoCobroMayorACeroRule;
    private final MontoCobroNoExcedeSaldoRule montoCobroNoExcedeSaldoRule;
    private final CuentaCobrarDisponibleParaCobroRule cuentaCobrarDisponibleParaCobroRule;

    public RegistrarNuevoCobroRulesValidatorImpl(
            MontoCobroMayorACeroRule montoCobroMayorACeroRule,
            MontoCobroNoExcedeSaldoRule montoCobroNoExcedeSaldoRule,
            CuentaCobrarDisponibleParaCobroRule cuentaCobrarDisponibleParaCobroRule) {
        this.montoCobroMayorACeroRule = montoCobroMayorACeroRule;
        this.montoCobroNoExcedeSaldoRule = montoCobroNoExcedeSaldoRule;
        this.cuentaCobrarDisponibleParaCobroRule = cuentaCobrarDisponibleParaCobroRule;
    }

    @Override
    public void validar(CobroDomain data) {
        montoCobroMayorACeroRule.validate(data.getMonto());
        cuentaCobrarDisponibleParaCobroRule.validate(data.getCuentaCobrar().getEstado());
        montoCobroNoExcedeSaldoRule.validate(data);
    }
}
