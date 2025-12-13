package com.agrosync.domain.abonos.rules.impl;

import com.agrosync.domain.abonos.AbonoDomain;
import com.agrosync.domain.abonos.exceptions.MontoAbonoExcedeSaldoException;
import com.agrosync.domain.abonos.rules.MontoAbonoNoExcedeSaldoRule;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class MontoAbonoNoExcedeSaldoRuleImpl implements MontoAbonoNoExcedeSaldoRule {

    @Override
    public void validate(AbonoDomain data) {
        BigDecimal monto = data.getMonto();
        BigDecimal saldoPendiente = data.getCuentaPagar().getSaldoPendiente();

        if (monto.compareTo(saldoPendiente) > 0) {
            throw MontoAbonoExcedeSaldoException.create();
        }
    }
}
