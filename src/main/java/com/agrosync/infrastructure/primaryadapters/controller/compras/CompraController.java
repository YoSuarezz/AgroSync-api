package com.agrosync.infrastructure.primaryadapters.controller.compras;

import com.agrosync.application.primaryports.dto.compras.request.AnularCompraDTO;
import com.agrosync.application.primaryports.dto.compras.request.CompraIdSuscripcionDTO;
import com.agrosync.application.primaryports.dto.compras.request.CompraPageDTO;
import com.agrosync.application.primaryports.dto.compras.request.RegistrarNuevaCompraDTO;
import com.agrosync.application.primaryports.dto.compras.response.ObtenerCompraDTO;
import com.agrosync.application.primaryports.dto.compras.response.ObtenerCompraDetalleDTO;
import com.agrosync.application.primaryports.interactor.compras.AnularCompraInteractor;
import com.agrosync.application.primaryports.interactor.compras.ObtenerComprasInteractor;
import com.agrosync.application.primaryports.interactor.compras.ObtenerCompraPorIdInteractor;
import com.agrosync.application.primaryports.interactor.compras.RegistrarNuevaCompraInteractor;
import com.agrosync.crosscutting.exception.custom.AgroSyncException;
import com.agrosync.infrastructure.primaryadapters.adapter.response.GenerateResponse;
import com.agrosync.infrastructure.primaryadapters.adapter.response.GenericResponse;
import com.agrosync.infrastructure.primaryadapters.adapter.response.PageResponse;
import com.agrosync.infrastructure.primaryadapters.adapter.response.compras.CompraResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/compra")
public class CompraController {

    private final RegistrarNuevaCompraInteractor registrarNuevaCompraInteractor;
    private final ObtenerComprasInteractor obtenerComprasInteractor;
    private final ObtenerCompraPorIdInteractor obtenerCompraPorIdInteractor;
    private final AnularCompraInteractor anularCompraInteractor;

    public CompraController(RegistrarNuevaCompraInteractor registrarNuevaCompraInteractor,
                            ObtenerComprasInteractor obtenerComprasInteractor,
                            ObtenerCompraPorIdInteractor obtenerCompraPorIdInteractor,
                            AnularCompraInteractor anularCompraInteractor) {
        this.registrarNuevaCompraInteractor = registrarNuevaCompraInteractor;
        this.obtenerComprasInteractor = obtenerComprasInteractor;
        this.obtenerCompraPorIdInteractor = obtenerCompraPorIdInteractor;
        this.anularCompraInteractor = anularCompraInteractor;
    }

    @PostMapping()
    public ResponseEntity<GenericResponse> registrarUsuario(@RequestBody RegistrarNuevaCompraDTO compra,
                                                            @RequestHeader(value = "x-suscripcion-id", required = false) UUID suscripcionId) {
        try {
            compra.setSuscripcionId(suscripcionId);
            registrarNuevaCompraInteractor.ejecutar(compra);
            return GenerateResponse.generateSuccessResponse(List.of("Se ha registrado la compra correctamente"));
        } catch (final AgroSyncException excepcion) {
            return GenerateResponse.generateBadRequestResponse(List.of(excepcion.getMensajeUsuario()));
        } catch (final Exception excepcion) {
            var userMessage = "Error al registrar la compra";
            return new ResponseEntity<>(GenericResponse.build(List.of(userMessage)), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<CompraResponse<PageResponse<ObtenerCompraDTO>>> consultarCompras(@RequestParam(defaultValue = "0") int page,
                                                                                           @RequestParam(defaultValue = "10") int size,
                                                                                           @RequestParam(defaultValue = "fechaCompra") String sortBy,
                                                                                           @RequestParam(defaultValue = "DESC") String sortDirection,
                                                                                           @RequestParam(required = false) String numeroCompra,
                                                                                           @RequestParam(required = false) UUID proveedorId,
                                                                                           @RequestParam(required = false) LocalDate fechaInicio,
                                                                                           @RequestParam(required = false) LocalDate fechaFin,
                                                                                           @RequestHeader(value = "x-suscripcion-id", required = false) UUID suscripcionId) {
        try {
            CompraPageDTO request = CompraPageDTO.create(page, size, sortBy, sortDirection, suscripcionId, fechaInicio, fechaFin, numeroCompra, proveedorId);
            PageResponse<ObtenerCompraDTO> resultados = obtenerComprasInteractor.ejecutar(request);

            CompraResponse<PageResponse<ObtenerCompraDTO>> response = CompraResponse.build(List.of("Compras consultadas correctamente"), resultados);
            return GenerateResponse.generateSuccessResponseWithData(response);
        } catch (final AgroSyncException excepcion) {
            var response = CompraResponse.build(List.of(excepcion.getMensajeUsuario()), PageResponse.from(Page.<ObtenerCompraDTO>empty()));
            return GenerateResponse.generateBadRequestResponseWithData(response);
        } catch (final IllegalArgumentException excepcion) {
            var response = CompraResponse.build(List.of(excepcion.getMessage()), PageResponse.from(Page.<ObtenerCompraDTO>empty()));
            return GenerateResponse.generateBadRequestResponseWithData(response);
        } catch (final Exception excepcion) {
            var response = CompraResponse.build(List.of("Error al consultar las Compras"), PageResponse.from(Page.<ObtenerCompraDTO>empty()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompraResponse<ObtenerCompraDetalleDTO>> consultarCompraPorId(@PathVariable UUID id,
                                                                                        @RequestHeader(value = "x-suscripcion-id", required = false) UUID suscripcionId) {
        try {
            CompraIdSuscripcionDTO request = CompraIdSuscripcionDTO.create(id, suscripcionId);
            ObtenerCompraDetalleDTO compra = obtenerCompraPorIdInteractor.ejecutar(request);
            var compraResponse = CompraResponse.build(List.of("Compra consultada correctamente"), compra);
            return GenerateResponse.generateSuccessResponseWithData(compraResponse);
        } catch (final AgroSyncException excepcion) {
            var compraResponse = CompraResponse.<ObtenerCompraDetalleDTO>build(List.of(excepcion.getMensajeUsuario()), null);
            return GenerateResponse.generateBadRequestResponseWithData(compraResponse);
        } catch (final Exception excepcion) {
            var userMessage = "Error al consultar la Compra";
            var compraResponse = CompraResponse.<ObtenerCompraDetalleDTO>build(List.of(userMessage), null);
            return new ResponseEntity<>(compraResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}/anular")
    public ResponseEntity<GenericResponse> anularCompra(@PathVariable UUID id,
                                                        @RequestBody AnularCompraDTO anularCompraDTO,
                                                        @RequestHeader(value = "x-suscripcion-id", required = false) UUID suscripcionId) {
        try {
            anularCompraDTO.setCompraId(id);
            anularCompraDTO.setSuscripcionId(suscripcionId);
            anularCompraInteractor.ejecutar(anularCompraDTO);
            return GenerateResponse.generateSuccessResponse(List.of("La compra ha sido anulada correctamente"));
        } catch (final AgroSyncException excepcion) {
            return GenerateResponse.generateBadRequestResponse(List.of(excepcion.getMensajeUsuario()));
        } catch (final Exception excepcion) {
            var userMessage = "Error al anular la compra";
            return new ResponseEntity<>(GenericResponse.build(List.of(userMessage)), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}