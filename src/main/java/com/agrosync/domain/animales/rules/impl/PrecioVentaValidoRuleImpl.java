package com.agrosync.domain.animales.rules.impl;

import com.agrosync.domain.animales.exceptions.PrecioVentaNoValidoException;
import com.agrosync.domain.animales.rules.PrecioVentaValidoRule;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PrecioVentaValidoRuleImpl implements PrecioVentaValidoRule {

    @Override
    public void validate(BigDecimal precio) {
        if (precio == null || precio.compareTo(BigDecimal.ZERO) <= 0) {
            throw PrecioVentaNoValidoException.create();
        }
    }
}
