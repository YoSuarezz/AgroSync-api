package com.agrosync.application.usecase.abonos.rulesvalidator.impl;

import com.agrosync.application.usecase.abonos.rulesvalidator.RegistrarNuevoAbonoRulesValidator;
import com.agrosync.domain.abonos.AbonoDomain;
import com.agrosync.domain.abonos.rules.CuentaPagarDisponibleParaAbonoRule;
import com.agrosync.domain.abonos.rules.FechaAbonoValidaRule;
import com.agrosync.domain.abonos.rules.MontoAbonoMayorACeroRule;
import com.agrosync.domain.abonos.rules.MontoAbonoNoExcedeSaldoRule;
import com.agrosync.domain.cuentaspagar.CuentaPagarDomain;
import com.agrosync.domain.cuentaspagar.rules.IdentificadorCuentaPagarExisteRule;
import com.agrosync.domain.suscripcion.rules.SuscripcionExisteRule;
import org.springframework.stereotype.Service;

@Service
public class RegistrarNuevoAbonoRulesValidatorImpl implements RegistrarNuevoAbonoRulesValidator {

    private final MontoAbonoMayorACeroRule montoAbonoMayorACeroRule;
    private final IdentificadorCuentaPagarExisteRule identificadorCuentaPagarExisteRule;
    private final MontoAbonoNoExcedeSaldoRule montoAbonoNoExcedeSaldoRule;
    private final CuentaPagarDisponibleParaAbonoRule cuentaPagarDisponibleParaAbonoRule;
    private final FechaAbonoValidaRule fechaAbonoValidaRule;
    private final SuscripcionExisteRule suscripcionExisteRule;

    public RegistrarNuevoAbonoRulesValidatorImpl(
            MontoAbonoMayorACeroRule montoAbonoMayorACeroRule,
            IdentificadorCuentaPagarExisteRule identificadorCuentaPagarExisteRule,
            MontoAbonoNoExcedeSaldoRule montoAbonoNoExcedeSaldoRule,
            CuentaPagarDisponibleParaAbonoRule cuentaPagarDisponibleParaAbonoRule,
            FechaAbonoValidaRule fechaAbonoValidaRule, SuscripcionExisteRule suscripcionExisteRule) {
        this.montoAbonoMayorACeroRule = montoAbonoMayorACeroRule;
        this.identificadorCuentaPagarExisteRule = identificadorCuentaPagarExisteRule;
        this.montoAbonoNoExcedeSaldoRule = montoAbonoNoExcedeSaldoRule;
        this.cuentaPagarDisponibleParaAbonoRule = cuentaPagarDisponibleParaAbonoRule;
        this.fechaAbonoValidaRule = fechaAbonoValidaRule;
        this.suscripcionExisteRule = suscripcionExisteRule;
    }

    @Override
    public void validar(AbonoDomain data) {

        CuentaPagarDomain cuentaPagar = data.getCuentaPagar();

        // --- Validación de existencia ---
        identificadorCuentaPagarExisteRule.validate(cuentaPagar.getId());

        // --- Validación de suscripción (CORRECTA) ---
        suscripcionExisteRule.validate(data.getSuscripcionId());

        // --- Validación del monto ---
        montoAbonoMayorACeroRule.validate(data.getMonto());
        montoAbonoNoExcedeSaldoRule.validate(data);

        // --- Validación de fecha ---
        fechaAbonoValidaRule.validate(data.getFechaPago());

        // --- Validación del estado ---
        cuentaPagarDisponibleParaAbonoRule.validate(cuentaPagar.getEstado());
    }
}
