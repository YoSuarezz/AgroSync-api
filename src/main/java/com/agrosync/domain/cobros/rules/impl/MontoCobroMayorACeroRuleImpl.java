package com.agrosync.domain.cobros.rules.impl;

import com.agrosync.domain.cobros.exceptions.MontoCobroNoValidoException;
import com.agrosync.domain.cobros.rules.MontoCobroMayorACeroRule;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class MontoCobroMayorACeroRuleImpl implements MontoCobroMayorACeroRule {

    @Override
    public void validate(BigDecimal monto) {
        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw MontoCobroNoValidoException.create();
        }
    }
}
