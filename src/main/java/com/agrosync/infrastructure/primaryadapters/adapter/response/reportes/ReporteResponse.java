package com.agrosync.infrastructure.primaryadapters.adapter.response.reportes;

import com.agrosync.application.primaryports.dto.reportes.response.DashboardReportDTO;
import com.agrosync.infrastructure.primaryadapters.adapter.response.ResponseWithData;

import java.util.List;

public class ReporteResponse extends ResponseWithData<DashboardReportDTO> {

    private ReporteResponse() {
        super();
    }

    public static ReporteResponse build(List<String> mensajes, DashboardReportDTO data) {
        ReporteResponse response = new ReporteResponse();
        response.setMensajes(mensajes);
        response.setDatos(data);
        return response;
    }
}
