package com.agrosync.infrastructure.primaryadapters.controller.suscripcion;

import com.agrosync.application.primaryports.dto.suscripcion.request.SuscripcionPageDTO;
import com.agrosync.application.primaryports.dto.suscripcion.response.ObtenerSuscripcionDTO;
import com.agrosync.application.primaryports.dto.suscripcion.response.ObtenerSuscripcionPorIdDTO;
import com.agrosync.application.primaryports.interactor.suscripcion.ObtenerSuscripcionPorIdInteractor;
import com.agrosync.application.primaryports.interactor.suscripcion.ObtenerSuscripcionesInteractor;
import com.agrosync.crosscutting.exception.custom.AgroSyncException;
import com.agrosync.infrastructure.primaryadapters.adapter.response.GenerateResponse;
import com.agrosync.infrastructure.primaryadapters.adapter.response.PageResponse;
import com.agrosync.infrastructure.primaryadapters.adapter.response.suscripcion.ObtenerSuscripcionPorIdResponse;
import com.agrosync.infrastructure.primaryadapters.adapter.response.suscripcion.ObtenerSuscripcionesResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/subscription")
public class SuscripcionController {

    private final ObtenerSuscripcionesInteractor obtenerSuscripcionesInteractor;
    private final ObtenerSuscripcionPorIdInteractor obtenerSuscripcionPorIdInteractor;

    public SuscripcionController(ObtenerSuscripcionesInteractor obtenerSuscripcionesInteractor,
                                 ObtenerSuscripcionPorIdInteractor obtenerSuscripcionPorIdInteractor) {
        this.obtenerSuscripcionesInteractor = obtenerSuscripcionesInteractor;
        this.obtenerSuscripcionPorIdInteractor = obtenerSuscripcionPorIdInteractor;
    }

    @GetMapping
    public ResponseEntity<ObtenerSuscripcionesResponse> consultarSuscripciones(@RequestParam(defaultValue = "0") int page,
                                                                              @RequestParam(defaultValue = "10") int size,
                                                                              @RequestParam(defaultValue = "nombreEmpresa") String sortBy,
                                                                              @RequestParam(defaultValue = "ASC") String sortDirection,
                                                                              @RequestParam(name = "nombreEmpresa", required = false) String nombreEmpresa,
                                                                              @RequestParam(name = "nit", required = false) String nit) {
        try {
            SuscripcionPageDTO suscripcionPageDTO = SuscripcionPageDTO.create(page, size, sortBy, sortDirection, nombreEmpresa, nit);
            PageResponse<ObtenerSuscripcionDTO> resultados = obtenerSuscripcionesInteractor.ejecutar(suscripcionPageDTO);
            ObtenerSuscripcionesResponse response = ObtenerSuscripcionesResponse.build(List.of("Suscripciones consultadas exitosamente"), resultados);
            return GenerateResponse.generateSuccessResponseWithData(response);
        } catch (final AgroSyncException excepcion) {
            var response = ObtenerSuscripcionesResponse.build(List.of(excepcion.getMensajeUsuario()), PageResponse.from(Page.empty()));
            return GenerateResponse.generateBadRequestResponseWithData(response);
        } catch (final Exception excepcion) {
            var userMessage = "Se ha presentado un problema tratando de consultar las suscripciones";
            var response = ObtenerSuscripcionesResponse.build(List.of(userMessage), PageResponse.from(Page.empty()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObtenerSuscripcionPorIdResponse> consultarSuscripcionPorId(@PathVariable UUID id) {
        try {
            ObtenerSuscripcionPorIdDTO resultado = obtenerSuscripcionPorIdInteractor.ejecutar(id);
            ObtenerSuscripcionPorIdResponse response = ObtenerSuscripcionPorIdResponse.build(List.of("Suscripción consultada exitosamente"), resultado);
            return GenerateResponse.generateSuccessResponseWithData(response);
        } catch (final AgroSyncException excepcion) {
            var response = ObtenerSuscripcionPorIdResponse.build(List.of(excepcion.getMensajeUsuario()), null);
            return GenerateResponse.generateBadRequestResponseWithData(response);
        } catch (final Exception excepcion) {
            var userMessage = "Se ha presentado un problema tratando de consultar la suscripción";
            var response = ObtenerSuscripcionPorIdResponse.build(List.of(userMessage), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
