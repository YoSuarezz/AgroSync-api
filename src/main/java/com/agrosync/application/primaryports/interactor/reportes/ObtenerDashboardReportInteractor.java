package com.agrosync.application.primaryports.interactor.reportes;

import com.agrosync.application.primaryports.dto.reportes.request.ReporteDashboardFiltroDTO;
import com.agrosync.application.primaryports.dto.reportes.response.DashboardReportDTO;
import com.agrosync.application.primaryports.interactor.InteractorWithReturn;

public interface ObtenerDashboardReportInteractor extends InteractorWithReturn<ReporteDashboardFiltroDTO, DashboardReportDTO> {
}
