package com.agrosync.application.usecase.reportes.rulesvalidator.impl;

import com.agrosync.application.usecase.reportes.rulesvalidator.ReporteDashboardRulesValidator;
import com.agrosync.crosscutting.exception.custom.AgroSyncException;
import com.agrosync.crosscutting.exception.enums.Layer;
import com.agrosync.domain.reportes.ReporteDashboardFiltroDomain;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ReporteDashboardRulesValidatorImpl implements ReporteDashboardRulesValidator {

    @Override
    public void validar(ReporteDashboardFiltroDomain filtro) {
        if (filtro == null) {
            throw new AgroSyncException("El filtro del reporte es requerido", Layer.DOMAIN);
        }

        LocalDate inicio = filtro.getFechaInicio();
        LocalDate fin = filtro.getFechaFin();

        if (inicio != null && fin != null && inicio.isAfter(fin)) {
            throw new AgroSyncException("La fecha inicial no puede ser mayor que la fecha final", Layer.DOMAIN);
        }
    }
}
