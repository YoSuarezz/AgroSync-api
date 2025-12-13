package com.agrosync.application.usecase.cobros.rulesvalidator.impl;

import com.agrosync.application.usecase.cobros.rulesvalidator.RegistrarNuevoCobroRulesValidator;
import com.agrosync.domain.cobros.CobroDomain;
import com.agrosync.domain.cobros.rules.CuentaCobrarDisponibleParaCobroRule;
import com.agrosync.domain.cobros.rules.FechaCobroValidaRule;
import com.agrosync.domain.cobros.rules.MontoCobroMayorACeroRule;
import com.agrosync.domain.cobros.rules.MontoCobroNoExcedeSaldoRule;
import com.agrosync.domain.cuentascobrar.CuentaCobrarDomain;
import com.agrosync.domain.cuentascobrar.rules.IdentificadorCuentaCobrarExisteRule;
import com.agrosync.domain.suscripcion.rules.SuscripcionExisteRule;
import org.springframework.stereotype.Service;

@Service
public class RegistrarNuevoCobroRulesValidatorImpl implements RegistrarNuevoCobroRulesValidator {

    private final MontoCobroMayorACeroRule montoCobroMayorACeroRule;
    private final IdentificadorCuentaCobrarExisteRule identificadorCuentaCobrarExisteRule;
    private final MontoCobroNoExcedeSaldoRule montoCobroNoExcedeSaldoRule;
    private final CuentaCobrarDisponibleParaCobroRule cuentaCobrarDisponibleParaCobroRule;
    private final FechaCobroValidaRule fechaCobroValidaRule;
    private final SuscripcionExisteRule suscripcionExisteRule;

    public RegistrarNuevoCobroRulesValidatorImpl(
            MontoCobroMayorACeroRule montoCobroMayorACeroRule,
            IdentificadorCuentaCobrarExisteRule identificadorCuentaCobrarExisteRule,
            MontoCobroNoExcedeSaldoRule montoCobroNoExcedeSaldoRule,
            CuentaCobrarDisponibleParaCobroRule cuentaCobrarDisponibleParaCobroRule,
            FechaCobroValidaRule fechaCobroValidaRule,
            SuscripcionExisteRule suscripcionExisteRule) {
        this.montoCobroMayorACeroRule = montoCobroMayorACeroRule;
        this.identificadorCuentaCobrarExisteRule = identificadorCuentaCobrarExisteRule;
        this.montoCobroNoExcedeSaldoRule = montoCobroNoExcedeSaldoRule;
        this.cuentaCobrarDisponibleParaCobroRule = cuentaCobrarDisponibleParaCobroRule;
        this.fechaCobroValidaRule = fechaCobroValidaRule;
        this.suscripcionExisteRule = suscripcionExisteRule;
    }

    @Override
    public void validar(CobroDomain data) {

        CuentaCobrarDomain cuentaCobrar = data.getCuentaCobrar();

        // --- Validación de existencia ---
        identificadorCuentaCobrarExisteRule.validate(cuentaCobrar.getId());

        // --- Validación de suscripción ---
        suscripcionExisteRule.validate(data.getSuscripcionId());

        // --- Validación del monto ---
        montoCobroMayorACeroRule.validate(data.getMonto());
        montoCobroNoExcedeSaldoRule.validate(data);

        // --- Validación de fecha ---
        fechaCobroValidaRule.validate(data.getFechaCobro());

        // --- Validación del estado ---
        cuentaCobrarDisponibleParaCobroRule.validate(cuentaCobrar.getEstado());
    }
}
