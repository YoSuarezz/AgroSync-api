package com.agrosync.domain.cobros.rules.impl;

import com.agrosync.domain.cobros.exceptions.FechaCobroNoValidaException;
import com.agrosync.domain.cobros.rules.FechaCobroValidaRule;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FechaCobroValidaRuleImpl implements FechaCobroValidaRule {

    private static final int DIAS_MAXIMO_ATRAS = 15;

    @Override
    public void validate(LocalDateTime fechaCobro) {
        if (fechaCobro == null) {
            throw FechaCobroNoValidaException.create();
        }

        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime fechaMinima = ahora.minusDays(DIAS_MAXIMO_ATRAS).toLocalDate().atStartOfDay();
        LocalDateTime fechaMaxima = ahora.toLocalDate().atTime(23, 59, 59);

        if (fechaCobro.isBefore(fechaMinima) || fechaCobro.isAfter(fechaMaxima)) {
            throw FechaCobroNoValidaException.create();
        }
    }
}

