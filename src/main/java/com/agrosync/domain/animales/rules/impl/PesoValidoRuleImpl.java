package com.agrosync.domain.animales.rules.impl;

import com.agrosync.domain.animales.exceptions.PesoNoValidoException;
import com.agrosync.domain.animales.rules.PesoValidoRule;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PesoValidoRuleImpl implements PesoValidoRule {

    @Override
    public void validate(BigDecimal peso) {

        if (peso == null || peso.compareTo(BigDecimal.ZERO) <= 0) {
            throw PesoNoValidoException.create(peso);
        }
    }
}
