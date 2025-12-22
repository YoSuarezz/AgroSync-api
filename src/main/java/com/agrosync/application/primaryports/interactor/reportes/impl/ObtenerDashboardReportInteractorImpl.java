package com.agrosync.application.primaryports.interactor.reportes.impl;

import com.agrosync.application.primaryports.dto.reportes.request.ReporteDashboardFiltroDTO;
import com.agrosync.application.primaryports.dto.reportes.response.DashboardReportDTO;
import com.agrosync.application.primaryports.interactor.reportes.ObtenerDashboardReportInteractor;
import com.agrosync.application.primaryports.mapper.reportes.ReportesDTOMapper;
import com.agrosync.application.usecase.reportes.ObtenerDashboardReport;
import com.agrosync.domain.reportes.DashboardReportDomain;
import com.agrosync.domain.reportes.ReporteDashboardFiltroDomain;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ObtenerDashboardReportInteractorImpl implements ObtenerDashboardReportInteractor {

    private final ObtenerDashboardReport obtenerDashboardReport;

    public ObtenerDashboardReportInteractorImpl(ObtenerDashboardReport obtenerDashboardReport) {
        this.obtenerDashboardReport = obtenerDashboardReport;
    }

    @Override
    public DashboardReportDTO ejecutar(ReporteDashboardFiltroDTO data) {
        ReporteDashboardFiltroDomain filtroDomain = ReportesDTOMapper.toDomain(data);
        DashboardReportDomain domain = obtenerDashboardReport.ejecutar(filtroDomain);
        return ReportesDTOMapper.toDTO(domain);
    }
}
