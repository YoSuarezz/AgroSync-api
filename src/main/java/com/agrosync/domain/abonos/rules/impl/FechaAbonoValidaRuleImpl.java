package com.agrosync.domain.abonos.rules.impl;

import com.agrosync.domain.abonos.exceptions.FechaAbonoNoValidaException;
import com.agrosync.domain.abonos.rules.FechaAbonoValidaRule;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FechaAbonoValidaRuleImpl implements FechaAbonoValidaRule {

    private static final int DIAS_MAXIMO_ATRAS = 15;

    @Override
    public void validate(LocalDateTime fechaPago) {
        if (fechaPago == null) {
            throw FechaAbonoNoValidaException.create();
        }

        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime fechaMinima = ahora.minusDays(DIAS_MAXIMO_ATRAS).toLocalDate().atStartOfDay();
        LocalDateTime fechaMaxima = ahora.toLocalDate().atTime(23, 59, 59);

        if (fechaPago.isBefore(fechaMinima) || fechaPago.isAfter(fechaMaxima)) {
            throw FechaAbonoNoValidaException.create();
        }
    }
}
