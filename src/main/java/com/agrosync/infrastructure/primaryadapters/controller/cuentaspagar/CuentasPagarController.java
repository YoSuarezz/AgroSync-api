package com.agrosync.infrastructure.primaryadapters.controller.cuentaspagar;

import com.agrosync.application.primaryports.dto.cuentaspagar.request.CuentaPagarIdSuscripcionDTO;
import com.agrosync.application.primaryports.dto.cuentaspagar.request.CuentaPagarPageDTO;
import com.agrosync.application.primaryports.dto.cuentaspagar.response.ObtenerCuentaPagarDTO;
import com.agrosync.application.primaryports.dto.usuarios.response.ObtenerUsuarioDTO;
import com.agrosync.application.primaryports.enums.cuentas.EstadoCuentaEnum;
import com.agrosync.application.primaryports.interactor.cuentaspagar.ObtenerCuentaPagarPorIdInteractor;
import com.agrosync.application.primaryports.interactor.cuentaspagar.ObtenerCuentasPagarInteractor;
import com.agrosync.crosscutting.exception.custom.AgroSyncException;
import com.agrosync.infrastructure.primaryadapters.adapter.response.GenerateResponse;
import com.agrosync.infrastructure.primaryadapters.adapter.response.PageResponse;
import com.agrosync.infrastructure.primaryadapters.adapter.response.cuentaspagar.CuentaPagarResponse; // Asumo la existencia de este DTO de respuesta
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cuentaspagar")
public class CuentasPagarController {

    private final ObtenerCuentasPagarInteractor obtenerCuentasPagarInteractor;
    private final ObtenerCuentaPagarPorIdInteractor obtenerCuentaPagarPorIdInteractor;

    public CuentasPagarController(ObtenerCuentasPagarInteractor obtenerCuentasPagarInteractor,
                                  ObtenerCuentaPagarPorIdInteractor obtenerCuentaPagarPorIdInteractor) {
        this.obtenerCuentasPagarInteractor = obtenerCuentasPagarInteractor;
        this.obtenerCuentaPagarPorIdInteractor = obtenerCuentaPagarPorIdInteractor;
    }

    @GetMapping
    public ResponseEntity<CuentaPagarResponse<PageResponse<ObtenerCuentaPagarDTO>>> consultarCuentasPagar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "fechaEmision") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection,
            @RequestParam(required = false) String numeroCuenta,
            @RequestParam(required = false, name = "proveedorId") UUID proveedorId,
            @RequestParam(required = false, name = "estado") EstadoCuentaEnum estado,
            @RequestHeader(value = "x-suscripcion-id", required = false) UUID suscripcionId) {

        try {
            ObtenerUsuarioDTO proveedorFiltro = proveedorId != null ? ObtenerUsuarioDTO.create(proveedorId, null, null, null) : null;

            ObtenerCuentaPagarDTO filtro = ObtenerCuentaPagarDTO.create(
                    null,
                    numeroCuenta,
                    proveedorFiltro,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            CuentaPagarPageDTO request = new CuentaPagarPageDTO(page, size, sortBy, sortDirection, filtro, estado, suscripcionId);

            PageResponse<ObtenerCuentaPagarDTO> resultado = obtenerCuentasPagarInteractor.ejecutar(request);

            var response = CuentaPagarResponse.build(List.of("Consulta de cuentas por pagar exitosa"), resultado);
            return GenerateResponse.generateSuccessResponseWithData(response);

        } catch (final AgroSyncException excepcion) {
            var response = CuentaPagarResponse.build(List.of(excepcion.getMensajeUsuario()), PageResponse.from(Page.<ObtenerCuentaPagarDTO>empty()));
            return GenerateResponse.generateBadRequestResponseWithData(response);
        } catch (final IllegalArgumentException excepcion) {
            var response = CuentaPagarResponse.build(List.of(excepcion.getMessage()), PageResponse.from(Page.<ObtenerCuentaPagarDTO>empty()));
            return GenerateResponse.generateBadRequestResponseWithData(response);
        } catch (final Exception excepcion) {
            var response = CuentaPagarResponse.build(List.of("Error al consultar las Cuentas por Pagar"), PageResponse.from(Page.<ObtenerCuentaPagarDTO>empty()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaPagarResponse<ObtenerCuentaPagarDTO>> consultarCuentaPagarPorId(
            @PathVariable UUID id,
            @RequestHeader(value = "x-suscripcion-id", required = false) UUID suscripcionId) {

        try {
            CuentaPagarIdSuscripcionDTO request = CuentaPagarIdSuscripcionDTO.create(id, suscripcionId);

            ObtenerCuentaPagarDTO cuentaPagar = obtenerCuentaPagarPorIdInteractor.ejecutar(request);

            var response = CuentaPagarResponse.build(List.of("Cuenta por pagar consultada correctamente"), cuentaPagar);
            return GenerateResponse.generateSuccessResponseWithData(response);

        } catch (final AgroSyncException excepcion) {
            var response = CuentaPagarResponse.<ObtenerCuentaPagarDTO>build(List.of(excepcion.getMensajeUsuario()), null);
            return GenerateResponse.generateBadRequestResponseWithData(response);
        } catch (final Exception excepcion) {
            var userMessage = "Error al consultar la Cuenta por Pagar";
            var response = CuentaPagarResponse.<ObtenerCuentaPagarDTO>build(List.of(userMessage), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}