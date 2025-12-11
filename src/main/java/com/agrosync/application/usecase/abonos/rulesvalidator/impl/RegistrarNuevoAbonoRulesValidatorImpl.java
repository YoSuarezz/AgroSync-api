package com.agrosync.application.usecase.abonos.rulesvalidator.impl;

import com.agrosync.application.usecase.abonos.rulesvalidator.RegistrarNuevoAbonoRulesValidator;
import com.agrosync.domain.abonos.AbonoDomain;
import com.agrosync.domain.abonos.rules.CuentaPagarDisponibleParaAbonoRule;
import com.agrosync.domain.abonos.rules.MontoAbonoMayorACeroRule;
import com.agrosync.domain.abonos.rules.MontoAbonoNoExcedeSaldoRule;
import org.springframework.stereotype.Service;

@Service
public class RegistrarNuevoAbonoRulesValidatorImpl implements RegistrarNuevoAbonoRulesValidator {

    private final MontoAbonoMayorACeroRule montoAbonoMayorACeroRule;
    private final MontoAbonoNoExcedeSaldoRule montoAbonoNoExcedeSaldoRule;
    private final CuentaPagarDisponibleParaAbonoRule cuentaPagarDisponibleParaAbonoRule;

    public RegistrarNuevoAbonoRulesValidatorImpl(
            MontoAbonoMayorACeroRule montoAbonoMayorACeroRule,
            MontoAbonoNoExcedeSaldoRule montoAbonoNoExcedeSaldoRule,
            CuentaPagarDisponibleParaAbonoRule cuentaPagarDisponibleParaAbonoRule) {
        this.montoAbonoMayorACeroRule = montoAbonoMayorACeroRule;
        this.montoAbonoNoExcedeSaldoRule = montoAbonoNoExcedeSaldoRule;
        this.cuentaPagarDisponibleParaAbonoRule = cuentaPagarDisponibleParaAbonoRule;
    }

    @Override
    public void validar(AbonoDomain data) {
        montoAbonoMayorACeroRule.validate(data.getMonto());
        cuentaPagarDisponibleParaAbonoRule.validate(data.getCuentaPagar().getEstado());
        montoAbonoNoExcedeSaldoRule.validate(data);
    }
}
