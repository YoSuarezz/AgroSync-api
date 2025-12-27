package com.agrosync.infrastructure.primaryadapters.controller.reportes;

import com.agrosync.application.primaryports.dto.reportes.request.ReporteDashboardFiltroDTO;
import com.agrosync.application.primaryports.dto.reportes.response.DashboardReportDTO;
import com.agrosync.application.primaryports.interactor.reportes.ObtenerDashboardReportInteractor;
import com.agrosync.crosscutting.exception.custom.AgroSyncException;
import com.agrosync.domain.enums.cuentas.MetodoPagoEnum;
import com.agrosync.infrastructure.primaryadapters.adapter.response.GenerateResponse;
import com.agrosync.infrastructure.primaryadapters.adapter.response.reportes.ReporteResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reportes")
public class ReporteController {

    private final ObtenerDashboardReportInteractor obtenerDashboardReportInteractor;

    public ReporteController(ObtenerDashboardReportInteractor obtenerDashboardReportInteractor) {
        this.obtenerDashboardReportInteractor = obtenerDashboardReportInteractor;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<ReporteResponse> obtenerDashboard(@RequestParam(required = false) LocalDate fechaInicio,
                                                            @RequestParam(required = false) LocalDate fechaFin,
                                                            @RequestParam(required = false) MetodoPagoEnum metodoPago,
                                                            @RequestParam(required = false) UUID clienteId,
                                                            @RequestParam(required = false) UUID proveedorId,
                                                            @RequestHeader(value = "x-suscripcion-id", required = false) UUID suscripcionId) {
        try {
            ReporteDashboardFiltroDTO filtro = ReporteDashboardFiltroDTO.create(fechaInicio, fechaFin, metodoPago, clienteId, proveedorId, suscripcionId);
            DashboardReportDTO reporte = obtenerDashboardReportInteractor.ejecutar(filtro);
            ReporteResponse response = ReporteResponse.build(List.of("Reporte generado correctamente"), reporte);
            return GenerateResponse.generateSuccessResponseWithData(response);
        } catch (final AgroSyncException excepcion) {
            ReporteResponse response = ReporteResponse.build(List.of(excepcion.getMensajeUsuario()), null);
            return GenerateResponse.generateBadRequestResponseWithData(response);
        } catch (final IllegalArgumentException excepcion) {
            ReporteResponse response = ReporteResponse.build(List.of(excepcion.getMessage()), null);
            return GenerateResponse.generateBadRequestResponseWithData(response);
        } catch (final Exception excepcion) {
            ReporteResponse response = ReporteResponse.build(List.of("Error al generar el reporte del dashboard"), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}