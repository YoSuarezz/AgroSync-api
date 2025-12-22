package com.agrosync.application.usecase.reportes;

import com.agrosync.application.usecase.UseCaseWithReturn;
import com.agrosync.domain.reportes.DashboardReportDomain;
import com.agrosync.domain.reportes.ReporteDashboardFiltroDomain;

public interface ObtenerDashboardReport extends UseCaseWithReturn<ReporteDashboardFiltroDomain, DashboardReportDomain> {
}
