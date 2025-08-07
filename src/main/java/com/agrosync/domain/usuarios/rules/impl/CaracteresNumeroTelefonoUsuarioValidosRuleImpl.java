package com.agrosync.domain.usuarios.rules.impl;

import com.agrosync.domain.usuarios.exceptions.CaracteresNumeroTelefonoUsuarioNoValidosException;
import com.agrosync.domain.usuarios.rules.CaracteresNumeroTelefonoUsuarioValidosRule;
import org.springframework.stereotype.Service;

@Service
public class CaracteresNumeroTelefonoUsuarioValidosRuleImpl implements CaracteresNumeroTelefonoUsuarioValidosRule {

    @Override
    public void validate(String data) {
        if (data.length() != 10) {
            throw CaracteresNumeroTelefonoUsuarioNoValidosException.create();
        }
    }
}
