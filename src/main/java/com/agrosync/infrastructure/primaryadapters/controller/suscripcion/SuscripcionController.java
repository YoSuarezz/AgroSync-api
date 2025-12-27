package com.agrosync.infrastructure.primaryadapters.controller.suscripcion;

import com.agrosync.application.primaryports.dto.suscripcion.request.ActualizarEstadoSuscripcionDTO;
import com.agrosync.application.primaryports.dto.suscripcion.request.ActualizarPlanSuscripcionDTO;
import com.agrosync.application.primaryports.dto.suscripcion.request.RegistrarSuscripcionDTO;
import com.agrosync.application.primaryports.dto.suscripcion.request.SuscripcionPageDTO;
import com.agrosync.application.primaryports.dto.suscripcion.response.ObtenerSuscripcionDTO;
import com.agrosync.application.primaryports.dto.suscripcion.response.ObtenerSuscripcionPorIdDTO;
import com.agrosync.application.primaryports.interactor.suscripcion.ActualizarEstadoSuscripcionInteractor;
import com.agrosync.application.primaryports.interactor.suscripcion.ObtenerSuscripcionPorIdInteractor;
import com.agrosync.application.primaryports.interactor.suscripcion.ObtenerSuscripcionesInteractor;
import com.agrosync.application.primaryports.interactor.suscripcion.RegistrarSuscripcionInteractor;
import com.agrosync.application.primaryports.interactor.suscripcion.ActualizarPlanSuscripcionInteractor;
import com.agrosync.crosscutting.exception.custom.AgroSyncException;
import com.agrosync.infrastructure.primaryadapters.adapter.response.GenerateResponse;
import com.agrosync.infrastructure.primaryadapters.adapter.response.GenericResponse;
import com.agrosync.infrastructure.primaryadapters.adapter.response.PageResponse;
import com.agrosync.infrastructure.primaryadapters.adapter.response.suscripcion.ObtenerSuscripcionPorIdResponse;
import com.agrosync.infrastructure.primaryadapters.adapter.response.suscripcion.ObtenerSuscripcionesResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/suscripcion")
public class SuscripcionController {

    private final RegistrarSuscripcionInteractor registrarSuscripcionInteractor;
    private final ObtenerSuscripcionesInteractor obtenerSuscripcionesInteractor;
    private final ObtenerSuscripcionPorIdInteractor obtenerSuscripcionPorIdInteractor;
    private final ActualizarEstadoSuscripcionInteractor actualizarEstadoSuscripcionInteractor;
    private final ActualizarPlanSuscripcionInteractor actualizarPlanSuscripcionInteractor;

    public SuscripcionController(RegistrarSuscripcionInteractor registrarSuscripcionInteractor,
                                 ObtenerSuscripcionesInteractor obtenerSuscripcionesInteractor,
                                 ObtenerSuscripcionPorIdInteractor obtenerSuscripcionPorIdInteractor,
                                 ActualizarEstadoSuscripcionInteractor actualizarEstadoSuscripcionInteractor,
                                 ActualizarPlanSuscripcionInteractor actualizarPlanSuscripcionInteractor) {
        this.registrarSuscripcionInteractor = registrarSuscripcionInteractor;
        this.obtenerSuscripcionesInteractor = obtenerSuscripcionesInteractor;
        this.obtenerSuscripcionPorIdInteractor = obtenerSuscripcionPorIdInteractor;
        this.actualizarEstadoSuscripcionInteractor = actualizarEstadoSuscripcionInteractor;
        this.actualizarPlanSuscripcionInteractor = actualizarPlanSuscripcionInteractor;
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMINISTRADOR')")
    public ResponseEntity<GenericResponse> registrarSuscripcion(@RequestBody RegistrarSuscripcionDTO suscripcionDTO) {
        try {
            registrarSuscripcionInteractor.ejecutar(suscripcionDTO);
            return GenerateResponse.generateSuccessResponse(List.of("Suscripcion registrada correctamente"));
        } catch (final AgroSyncException excepcion) {
            return GenerateResponse.generateBadRequestResponse(List.of(excepcion.getMensajeUsuario()));
        } catch (final Exception excepcion) {
            var userMessage = "Se ha presentado un problema tratando de registrar la suscripcion";
            return new ResponseEntity<>(GenericResponse.build(List.of(userMessage)), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMINISTRADOR')")
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
            excepcion.printStackTrace();
            var userMessage = "Se ha presentado un problema tratando de consultar las suscripciones";
            var response = ObtenerSuscripcionesResponse.build(List.of(userMessage), PageResponse.from(Page.empty()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMINISTRADOR')")
    public ResponseEntity<ObtenerSuscripcionPorIdResponse> consultarSuscripcionPorId(@PathVariable UUID id) {
        try {
            ObtenerSuscripcionPorIdDTO resultado = obtenerSuscripcionPorIdInteractor.ejecutar(id);
            ObtenerSuscripcionPorIdResponse response = ObtenerSuscripcionPorIdResponse.build(List.of("Suscripción consultada exitosamente"), resultado);
            return GenerateResponse.generateSuccessResponseWithData(response);
        } catch (final AgroSyncException excepcion) {
            var response = ObtenerSuscripcionPorIdResponse.build(List.of(excepcion.getMensajeUsuario()), null);
            return GenerateResponse.generateBadRequestResponseWithData(response);
        } catch (final Exception excepcion) {
            excepcion.printStackTrace();
            var userMessage = "Se ha presentado un problema tratando de consultar la suscripción";
            var response = ObtenerSuscripcionPorIdResponse.build(List.of(userMessage), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}/estado")
    @PreAuthorize("hasRole('SUPER_ADMINISTRADOR')")
    public ResponseEntity<GenericResponse> actualizarEstadoSuscripcion(@PathVariable UUID id,
                                                                       @RequestBody ActualizarEstadoSuscripcionDTO data) {
        try {
            data.setId(id);
            actualizarEstadoSuscripcionInteractor.ejecutar(data);
            return GenerateResponse.generateSuccessResponse(List.of("Estado de la suscripcion actualizado correctamente"));
        } catch (final AgroSyncException excepcion) {
            return GenerateResponse.generateBadRequestResponse(List.of(excepcion.getMensajeUsuario()));
        } catch (final Exception excepcion) {
            excepcion.printStackTrace();
            var userMessage = "Se ha presentado un problema tratando de actualizar el estado de la suscripcion";
            return new ResponseEntity<>(GenericResponse.build(List.of(userMessage)), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}/plan")
    @PreAuthorize("hasRole('SUPER_ADMINISTRADOR')")
    public ResponseEntity<GenericResponse> actualizarPlanSuscripcion(@PathVariable UUID id,
                                                                     @RequestBody ActualizarPlanSuscripcionDTO data) {
        try {
            data.setId(id);
            actualizarPlanSuscripcionInteractor.ejecutar(data);
            return GenerateResponse.generateSuccessResponse(List.of("Plan de la suscripcion actualizado correctamente"));
        } catch (final AgroSyncException excepcion) {
            return GenerateResponse.generateBadRequestResponse(List.of(excepcion.getMensajeUsuario()));
        } catch (final Exception excepcion) {
            excepcion.printStackTrace();
            var userMessage = "Se ha presentado un problema tratando de actualizar el plan de la suscripcion";
            return new ResponseEntity<>(GenericResponse.build(List.of(userMessage)), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}