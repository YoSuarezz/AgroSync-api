package com.agrosync.domain.cobros.rules.impl;

import com.agrosync.domain.cobros.CobroDomain;
import com.agrosync.domain.cobros.exceptions.MontoCobroExcedeSaldoException;
import com.agrosync.domain.cobros.rules.MontoCobroNoExcedeSaldoRule;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class MontoCobroNoExcedeSaldoRuleImpl implements MontoCobroNoExcedeSaldoRule {

    @Override
    public void validate(CobroDomain data) {
        BigDecimal monto = data.getMonto();
        BigDecimal saldoPendiente = data.getCuentaCobrar().getSaldoPendiente();

        if (monto.compareTo(saldoPendiente) > 0) {
            throw MontoCobroExcedeSaldoException.create();
        }
    }
}
