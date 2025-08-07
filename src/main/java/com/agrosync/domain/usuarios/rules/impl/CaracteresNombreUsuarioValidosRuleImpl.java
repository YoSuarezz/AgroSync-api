package com.agrosync.domain.usuarios.rules.impl;

import com.agrosync.domain.usuarios.exceptions.CaracteresNombreUsuarioNoValidosException;
import com.agrosync.domain.usuarios.rules.CaracteresNombreUsuarioValidosRule;
import org.springframework.stereotype.Service;

@Service
public class CaracteresNombreUsuarioValidosRuleImpl implements CaracteresNombreUsuarioValidosRule {

    @Override
    public void validate(String data) {
        if (data.length() > 50) {
            throw CaracteresNombreUsuarioNoValidosException.create();
        }
    }
}
