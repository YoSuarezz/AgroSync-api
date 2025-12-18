package com.agrosync.domain.animales.rules.impl;

import com.agrosync.domain.enums.animales.SexoEnum;
import com.agrosync.domain.animales.exceptions.SexoNoValidoException;
import com.agrosync.domain.animales.rules.SexoValidoRule;
import org.springframework.stereotype.Service;

@Service
public class SexoValidoRuleImpl implements SexoValidoRule {

    @Override
    public void validate(SexoEnum sexo) {

        if (sexo == null) {
            throw SexoNoValidoException.create();
        }
    }
}
