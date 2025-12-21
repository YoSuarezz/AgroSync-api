package com.agrosync.application.usecase.usuarios.rulesvalidator.impl;

import com.agrosync.application.usecase.usuarios.rulesvalidator.ActualizarUsuarioRulesValidator;
import com.agrosync.domain.usuarios.UsuarioDomain;
import com.agrosync.domain.usuarios.rules.*;
import com.agrosync.domain.suscripcion.rules.SuscripcionExisteRule;
import org.springframework.stereotype.Service;

@Service
public class ActualizarUsuarioRulesValidatorImpl implements ActualizarUsuarioRulesValidator {

    private final UsuarioIdExisteRule usuarioIdExisteRule;
    private final CaracteresNombreUsuarioValidosRule caracteresNombreUsuarioValidosRule;
    private final CaracteresNumeroTelefonoUsuarioValidosRule caracteresNumeroTelefonoUsuarioValidosRule;
    private final ActualizarNombreUsuarioNoExisteRule actualizarNombreUsuarioNoExisteRule;
    private final ActualizarNumeroTelefonoUsuarioNoExisteRule actualizarNumeroTelefonoUsuarioNoExisteRule;
    private final SuscripcionExisteRule suscripcionExisteRule;

    public ActualizarUsuarioRulesValidatorImpl(UsuarioIdExisteRule usuarioIdExisteRule, CaracteresNombreUsuarioValidosRule caracteresNombreUsuarioValidosRule, CaracteresNumeroTelefonoUsuarioValidosRule caracteresNumeroTelefonoUsuarioValidosRule, ActualizarNombreUsuarioNoExisteRule actualizarNombreUsuarioNoExisteRule, ActualizarNumeroTelefonoUsuarioNoExisteRule actualizarNumeroTelefonoUsuarioNoExisteRule, SuscripcionExisteRule suscripcionExisteRule) {
        this.usuarioIdExisteRule = usuarioIdExisteRule;
        this.caracteresNombreUsuarioValidosRule = caracteresNombreUsuarioValidosRule;
        this.caracteresNumeroTelefonoUsuarioValidosRule = caracteresNumeroTelefonoUsuarioValidosRule;
        this.actualizarNombreUsuarioNoExisteRule = actualizarNombreUsuarioNoExisteRule;
        this.actualizarNumeroTelefonoUsuarioNoExisteRule = actualizarNumeroTelefonoUsuarioNoExisteRule;
        this.suscripcionExisteRule = suscripcionExisteRule;
    }

    @Override
    public void validar(UsuarioDomain data) {
        suscripcionExisteRule.validate(data.getSuscripcionId());
        usuarioIdExisteRule.validate(data.getId());
        caracteresNombreUsuarioValidosRule.validate(data.getNombre());
        caracteresNumeroTelefonoUsuarioValidosRule.validate(data.getTelefono());
        actualizarNombreUsuarioNoExisteRule.validate(data);
        actualizarNumeroTelefonoUsuarioNoExisteRule.validate(data);
    }
}
