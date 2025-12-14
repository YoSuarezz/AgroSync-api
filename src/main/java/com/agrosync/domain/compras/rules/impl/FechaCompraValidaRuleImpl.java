package com.agrosync.domain.compras.rules.impl;

import com.agrosync.domain.compras.exceptions.FechaCompraFueraDeRangoException;
import com.agrosync.domain.compras.rules.FechaCompraValidaRule;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class FechaCompraValidaRuleImpl implements FechaCompraValidaRule {

    private static final int DIAS_PERMITIDOS_ATRAS = 15;

    @Override
    public void validate(LocalDate fechaCompra) {
        if (fechaCompra == null) {
            return; // Si es null, se usará la fecha actual en el caso de uso
        }

        LocalDate hoy = LocalDate.now();
        LocalDate fechaLimiteAtras = hoy.minusDays(DIAS_PERMITIDOS_ATRAS);

        // La fecha no puede ser futura
        if (fechaCompra.isAfter(hoy)) {
            throw FechaCompraFueraDeRangoException.create();
        }

        // La fecha no puede ser anterior a 15 días
        if (fechaCompra.isBefore(fechaLimiteAtras)) {
            throw FechaCompraFueraDeRangoException.create();
        }
    }
}

