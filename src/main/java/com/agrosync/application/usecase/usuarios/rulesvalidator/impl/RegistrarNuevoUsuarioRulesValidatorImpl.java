package com.agrosync.application.usecase.usuarios.rulesvalidator.impl;

import com.agrosync.application.usecase.usuarios.rulesvalidator.RegistrarNuevoUsuarioRulesValidator;
import com.agrosync.domain.usuarios.UsuarioDomain;
import com.agrosync.domain.usuarios.rules.CaracteresNombreUsuarioValidosRule;
import com.agrosync.domain.usuarios.rules.CaracteresNumeroTelefonoUsuarioValidosRule;
import com.agrosync.domain.usuarios.rules.NombreUsuarioNoExisteRule;
import com.agrosync.domain.usuarios.rules.NumeroTelefonoUsuarioNoExisteRule;
import com.agrosync.domain.suscripcion.rules.SuscripcionExisteRule;
import org.springframework.stereotype.Service;

@Service
public class RegistrarNuevoUsuarioRulesValidatorImpl implements RegistrarNuevoUsuarioRulesValidator {

    private final NombreUsuarioNoExisteRule nombreUsuarioNoExisteRule;
    private final NumeroTelefonoUsuarioNoExisteRule numeroTelefonoUsuarioNoExisteRule;
    private final CaracteresNombreUsuarioValidosRule caracteresNombreUsuarioValidosRule;
    private final CaracteresNumeroTelefonoUsuarioValidosRule caracteresNumeroTelefonoUsuarioValidosRule;
    private final SuscripcionExisteRule suscripcionExisteRule;

    public RegistrarNuevoUsuarioRulesValidatorImpl(NombreUsuarioNoExisteRule nombreUsuarioNoExisteRule, NumeroTelefonoUsuarioNoExisteRule numeroTelefonoUsuarioNoExisteRule, CaracteresNombreUsuarioValidosRule caracteresNombreUsuarioValidosRule, CaracteresNumeroTelefonoUsuarioValidosRule caracteresNumeroTelefonoUsuarioValidosRule, SuscripcionExisteRule suscripcionExisteRule) {
        this.nombreUsuarioNoExisteRule = nombreUsuarioNoExisteRule;
        this.numeroTelefonoUsuarioNoExisteRule = numeroTelefonoUsuarioNoExisteRule;
        this.caracteresNombreUsuarioValidosRule = caracteresNombreUsuarioValidosRule;
        this.caracteresNumeroTelefonoUsuarioValidosRule = caracteresNumeroTelefonoUsuarioValidosRule;
        this.suscripcionExisteRule = suscripcionExisteRule;
    }

    @Override
    public void validar(UsuarioDomain data) {
        suscripcionExisteRule.validate(data.getSuscripcionId());
        nombreUsuarioNoExisteRule.validate(data);
        numeroTelefonoUsuarioNoExisteRule.validate(data);
        caracteresNombreUsuarioValidosRule.validate(data.getNombre());
        caracteresNumeroTelefonoUsuarioValidosRule.validate(data.getTelefono());
    }
}
