package com.agrosync.domain.ventas.rules.impl;

import com.agrosync.domain.ventas.exceptions.FechaVentaFueraDeRangoException;
import com.agrosync.domain.ventas.rules.FechaVentaValidaRule;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class FechaVentaValidaRuleImpl implements FechaVentaValidaRule {

    private static final int DIAS_PERMITIDOS_ATRAS = 15;

    @Override
    public void validate(LocalDate fechaVenta) {
        if (fechaVenta == null) {
            return; // Si es null, se usará la fecha actual en el caso de uso
        }

        LocalDate hoy = LocalDate.now();
        LocalDate fechaLimiteAtras = hoy.minusDays(DIAS_PERMITIDOS_ATRAS);

        // La fecha no puede ser futura
        if (fechaVenta.isAfter(hoy)) {
            throw FechaVentaFueraDeRangoException.create();
        }

        // La fecha no puede ser anterior a 15 días
        if (fechaVenta.isBefore(fechaLimiteAtras)) {
            throw FechaVentaFueraDeRangoException.create();
        }
    }
}

