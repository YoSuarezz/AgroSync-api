package com.agrosync.application.usecase.usuarios.usuario.rulesvalidator.impl;

import com.agrosync.application.usecase.usuarios.usuario.rulesvalidator.RegistrarNuevoUsuarioRulesValidator;
import com.agrosync.domain.usuarios.UsuarioDomain;
import com.agrosync.domain.usuarios.rules.CaracteresNombreUsuarioValidosRule;
import com.agrosync.domain.usuarios.rules.CaracteresNumeroTelefonoUsuarioValidosRule;
import com.agrosync.domain.usuarios.rules.NombreUsuarioNoExisteRule;
import com.agrosync.domain.usuarios.rules.NumeroTelefonoUsuarioNoExisteRule;
import org.springframework.stereotype.Service;

@Service
public class RegistrarNuevoUsuarioRulesValidatorImpl implements RegistrarNuevoUsuarioRulesValidator {

    private final NombreUsuarioNoExisteRule nombreUsuarioNoExisteRule;
    private final NumeroTelefonoUsuarioNoExisteRule numeroTelefonoUsuarioNoExisteRule;
    private final CaracteresNombreUsuarioValidosRule caracteresNombreUsuarioValidosRule;
    private final CaracteresNumeroTelefonoUsuarioValidosRule caracteresNumeroTelefonoUsuarioValidosRule;

    public RegistrarNuevoUsuarioRulesValidatorImpl(NombreUsuarioNoExisteRule nombreUsuarioNoExisteRule, NumeroTelefonoUsuarioNoExisteRule numeroTelefonoUsuarioNoExisteRule, CaracteresNombreUsuarioValidosRule caracteresNombreUsuarioValidosRule, CaracteresNumeroTelefonoUsuarioValidosRule caracteresNumeroTelefonoUsuarioValidosRule) {
        this.nombreUsuarioNoExisteRule = nombreUsuarioNoExisteRule;
        this.numeroTelefonoUsuarioNoExisteRule = numeroTelefonoUsuarioNoExisteRule;
        this.caracteresNombreUsuarioValidosRule = caracteresNombreUsuarioValidosRule;
        this.caracteresNumeroTelefonoUsuarioValidosRule = caracteresNumeroTelefonoUsuarioValidosRule;
    }

    @Override
    public void validar(UsuarioDomain data) {
        nombreUsuarioNoExisteRule.validate(data.getNombre());
        numeroTelefonoUsuarioNoExisteRule.validate(data.getTelefono());
        caracteresNombreUsuarioValidosRule.validate(data.getNombre());
        caracteresNumeroTelefonoUsuarioValidosRule.validate(data.getTelefono());
    }
}
