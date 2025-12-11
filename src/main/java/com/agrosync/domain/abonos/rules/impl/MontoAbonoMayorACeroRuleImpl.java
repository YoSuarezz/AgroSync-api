package com.agrosync.domain.abonos.rules.impl;

import com.agrosync.domain.abonos.exceptions.MontoAbonoNoValidoException;
import com.agrosync.domain.abonos.rules.MontoAbonoMayorACeroRule;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class MontoAbonoMayorACeroRuleImpl implements MontoAbonoMayorACeroRule {

    @Override
    public void validate(BigDecimal monto) {
        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw MontoAbonoNoValidoException.create();
        }
    }
}
