package com.agrosync.infrastructure.primaryadapters.controller.ventas;

import com.agrosync.application.primaryports.dto.ventas.request.AnularVentaDTO;
import com.agrosync.application.primaryports.dto.ventas.request.RegistrarNuevaVentaDTO;
import com.agrosync.application.primaryports.dto.ventas.request.VentaIdSuscripcionDTO;
import com.agrosync.application.primaryports.dto.ventas.request.VentaPageDTO;
import com.agrosync.application.primaryports.dto.ventas.response.ObtenerVentaDetalleDTO;
import com.agrosync.application.primaryports.dto.ventas.response.ObtenerVentasDTO;
import com.agrosync.application.primaryports.interactor.ventas.AnularVentaInteractor;
import com.agrosync.application.primaryports.interactor.ventas.ObtenerVentaPorIdInteractor;
import com.agrosync.application.primaryports.interactor.ventas.ObtenerVentasInteractor;
import com.agrosync.application.primaryports.interactor.ventas.RegistrarNuevaVentaInteractor;
import com.agrosync.crosscutting.exception.custom.AgroSyncException;
import com.agrosync.infrastructure.primaryadapters.adapter.response.GenerateResponse;
import com.agrosync.infrastructure.primaryadapters.adapter.response.GenericResponse;
import com.agrosync.infrastructure.primaryadapters.adapter.response.PageResponse;
import com.agrosync.infrastructure.primaryadapters.adapter.response.ventas.VentaResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/venta")
public class VentaController {

    private final RegistrarNuevaVentaInteractor registrarNuevaVentaInteractor;
    private final ObtenerVentasInteractor obtenerVentasInteractor;
    private final ObtenerVentaPorIdInteractor obtenerVentaPorIdInteractor;
    private final AnularVentaInteractor anularVentaInteractor;

    public VentaController(RegistrarNuevaVentaInteractor registrarNuevaVentaInteractor,
                           ObtenerVentasInteractor obtenerVentasInteractor,
                           ObtenerVentaPorIdInteractor obtenerVentaPorIdInteractor,
                           AnularVentaInteractor anularVentaInteractor) {
        this.registrarNuevaVentaInteractor = registrarNuevaVentaInteractor;
        this.obtenerVentasInteractor = obtenerVentasInteractor;
        this.obtenerVentaPorIdInteractor = obtenerVentaPorIdInteractor;
        this.anularVentaInteractor = anularVentaInteractor;
    }

    @PostMapping
    public ResponseEntity<GenericResponse> registrarVenta(@RequestBody RegistrarNuevaVentaDTO venta,
                                                          @RequestHeader(value = "x-suscripcion-id", required = false) UUID suscripcionId) {
        try {
            venta.setSuscripcionId(suscripcionId);
            registrarNuevaVentaInteractor.ejecutar(venta);
            return GenerateResponse.generateSuccessResponse(List.of("Se ha registrado la venta correctamente"));
        } catch (final AgroSyncException excepcion) {
            return GenerateResponse.generateBadRequestResponse(List.of(excepcion.getMensajeUsuario()));
        } catch (final Exception excepcion) {
            var userMessage = "Error al registrar la venta";
            return new ResponseEntity<>(GenericResponse.build(List.of(userMessage)), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<VentaResponse<PageResponse<ObtenerVentasDTO>>> consultarVentas(@RequestParam(defaultValue = "0") int page,
                                                                                         @RequestParam(defaultValue = "10") int size,
                                                                                         @RequestParam(defaultValue = "fechaVenta") String sortBy,
                                                                                         @RequestParam(defaultValue = "DESC") String sortDirection,
                                                                                         @RequestParam(required = false) String numeroVenta,
                                                                                         @RequestParam(required = false) UUID clienteId,
                                                                                         @RequestParam(required = false) LocalDate fechaInicio,
                                                                                         @RequestParam(required = false) LocalDate fechaFin,
                                                                                         @RequestHeader(value = "x-suscripcion-id", required = false) UUID suscripcionId) {

        try {
            VentaPageDTO request = VentaPageDTO.create(page, size, sortBy, sortDirection, ObtenerVentasDTO.create(), fechaInicio, fechaFin, numeroVenta, suscripcionId, clienteId);

            PageResponse<ObtenerVentasDTO> resultados = obtenerVentasInteractor.ejecutar(request);
            VentaResponse<PageResponse<ObtenerVentasDTO>> response = VentaResponse.build(List.of("Ventas consultadas correctamente"), resultados);
            return GenerateResponse.generateSuccessResponseWithData(response);

        } catch (final AgroSyncException excepcion) {
            var response = VentaResponse.build(List.of(excepcion.getMensajeUsuario()), PageResponse.from(Page.<ObtenerVentasDTO>empty()));
            return GenerateResponse.generateBadRequestResponseWithData(response);
        } catch (final IllegalArgumentException excepcion) {
            var response = VentaResponse.build(List.of(excepcion.getMessage()), PageResponse.from(Page.<ObtenerVentasDTO>empty()));
            return GenerateResponse.generateBadRequestResponseWithData(response);
        } catch (final Exception excepcion) {
            var response = VentaResponse.build(List.of("Error al consultar las Ventas"), PageResponse.from(Page.<ObtenerVentasDTO>empty()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaResponse<ObtenerVentaDetalleDTO>> consultarVentaPorId(@PathVariable UUID id,
                                                                                     @RequestHeader(value = "x-suscripcion-id", required = false) UUID suscripcionId) {
        try {
            VentaIdSuscripcionDTO request = VentaIdSuscripcionDTO.create(id, suscripcionId);
            ObtenerVentaDetalleDTO venta = obtenerVentaPorIdInteractor.ejecutar(request);
            var ventaResponse = VentaResponse.build(List.of("Venta consultada correctamente"), venta);
            return GenerateResponse.generateSuccessResponseWithData(ventaResponse);
        } catch (final AgroSyncException excepcion) {
            var ventaResponse = VentaResponse.<ObtenerVentaDetalleDTO>build(List.of(excepcion.getMensajeUsuario()), null);
            return GenerateResponse.generateBadRequestResponseWithData(ventaResponse);
        } catch (final Exception excepcion) {
            var userMessage = "Error al consultar la Venta";
            var ventaResponse = VentaResponse.<ObtenerVentaDetalleDTO>build(List.of(userMessage), null);
            return new ResponseEntity<>(ventaResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}/anular")
    public ResponseEntity<GenericResponse> anularVenta(@PathVariable UUID id,
                                                       @RequestBody AnularVentaDTO anularVentaDTO,
                                                       @RequestHeader(value = "x-suscripcion-id", required = false) UUID suscripcionId) {
        try {
            anularVentaDTO.setVentaId(id);
            anularVentaDTO.setSuscripcionId(suscripcionId);
            anularVentaInteractor.ejecutar(anularVentaDTO);
            return GenerateResponse.generateSuccessResponse(List.of("La venta ha sido anulada correctamente"));
        } catch (final AgroSyncException excepcion) {
            return GenerateResponse.generateBadRequestResponse(List.of(excepcion.getMensajeUsuario()));
        } catch (final Exception excepcion) {
            var userMessage = "Error al anular la venta";
            return new ResponseEntity<>(GenericResponse.build(List.of(userMessage)), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
