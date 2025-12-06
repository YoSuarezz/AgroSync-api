package com.agrosync.domain.animales.rules.impl;

import com.agrosync.domain.animales.exceptions.PrecioCompraNoValidoException;
import com.agrosync.domain.animales.rules.PrecioCompraValidoRule;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PrecioCompraValidoRuleImpl implements PrecioCompraValidoRule {

    @Override
    public void validate(BigDecimal precio) {

        if (precio == null || precio.compareTo(BigDecimal.ZERO) <= 0) {
            throw PrecioCompraNoValidoException.create(precio);
        }
    }
}
