package com.agrosync.infrastructure.primaryadapters.controller.cuentascobrar;

import com.agrosync.application.primaryports.dto.cuentascobrar.request.CuentaCobrarIdSuscripcionDTO;
import com.agrosync.application.primaryports.dto.cuentascobrar.request.CuentaCobrarPageDTO;
import com.agrosync.application.primaryports.dto.cuentascobrar.response.ObtenerCuentaCobrarDTO;
import com.agrosync.application.primaryports.dto.usuarios.response.ObtenerUsuarioDTO;
import com.agrosync.application.primaryports.enums.cuentas.EstadoCuentaEnum;
import com.agrosync.application.primaryports.interactor.cuentascobrar.ObtenerCuentaCobrarPorIdInteractor;
import com.agrosync.application.primaryports.interactor.cuentascobrar.ObtenerCuentasCobrarInteractor;
import com.agrosync.crosscutting.exception.custom.AgroSyncException;
import com.agrosync.infrastructure.primaryadapters.adapter.response.GenerateResponse;
import com.agrosync.infrastructure.primaryadapters.adapter.response.PageResponse;
import com.agrosync.infrastructure.primaryadapters.adapter.response.cuentascobrar.CuentaCobrarResponse; // Asumimos la existencia
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cuentascobrar")
public class CuentasCobrarController {

    private final ObtenerCuentasCobrarInteractor obtenerCuentasCobrarInteractor;
    private final ObtenerCuentaCobrarPorIdInteractor obtenerCuentaCobrarPorIdInteractor;

    public CuentasCobrarController(ObtenerCuentasCobrarInteractor obtenerCuentasCobrarInteractor,
                                   ObtenerCuentaCobrarPorIdInteractor obtenerCuentaCobrarPorIdInteractor) {
        this.obtenerCuentasCobrarInteractor = obtenerCuentasCobrarInteractor;
        this.obtenerCuentaCobrarPorIdInteractor = obtenerCuentaCobrarPorIdInteractor;
    }

    @GetMapping
    public ResponseEntity<CuentaCobrarResponse<PageResponse<ObtenerCuentaCobrarDTO>>> consultarCuentasCobrar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "fechaEmision") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection,
            @RequestParam(required = false) String numeroCuenta,
            @RequestParam(required = false, name = "clienteId") UUID clienteId, // Filtrado por ID del cliente
            @RequestParam(required = false, name = "estado") EstadoCuentaEnum estado,
            @RequestHeader(value = "x-suscripcion-id", required = false) UUID suscripcionId) {

        try {
            ObtenerUsuarioDTO clienteFiltro = clienteId != null ? ObtenerUsuarioDTO.create(clienteId, null, null, null) : null;

            ObtenerCuentaCobrarDTO filtro = ObtenerCuentaCobrarDTO.create(
                    null,
                    numeroCuenta,
                    clienteFiltro,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            CuentaCobrarPageDTO request = new CuentaCobrarPageDTO(page, size, sortBy, sortDirection, filtro, estado, suscripcionId);

            PageResponse<ObtenerCuentaCobrarDTO> resultado = obtenerCuentasCobrarInteractor.ejecutar(request);

            var response = CuentaCobrarResponse.build(List.of("Consulta de cuentas por cobrar exitosa"), resultado);
            return GenerateResponse.generateSuccessResponseWithData(response);

        } catch (final AgroSyncException excepcion) {
            var response = CuentaCobrarResponse.build(List.of(excepcion.getMensajeUsuario()), PageResponse.from(Page.<ObtenerCuentaCobrarDTO>empty()));
            return GenerateResponse.generateBadRequestResponseWithData(response);
        } catch (final IllegalArgumentException excepcion) {
            var response = CuentaCobrarResponse.build(List.of(excepcion.getMessage()), PageResponse.from(Page.<ObtenerCuentaCobrarDTO>empty()));
            return GenerateResponse.generateBadRequestResponseWithData(response);
        } catch (final Exception excepcion) {
            var response = CuentaCobrarResponse.build(List.of("Error al consultar las Cuentas por Cobrar"), PageResponse.from(Page.<ObtenerCuentaCobrarDTO>empty()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaCobrarResponse<ObtenerCuentaCobrarDTO>> consultarCuentaCobrarPorId(
            @PathVariable UUID id,
            @RequestHeader(value = "x-suscripcion-id", required = false) UUID suscripcionId) {

        try {
            CuentaCobrarIdSuscripcionDTO request = CuentaCobrarIdSuscripcionDTO.create(id, suscripcionId);

            ObtenerCuentaCobrarDTO cuentaCobrar = obtenerCuentaCobrarPorIdInteractor.ejecutar(request);

            var response = CuentaCobrarResponse.build(List.of("Cuenta por cobrar consultada correctamente"), cuentaCobrar);
            return GenerateResponse.generateSuccessResponseWithData(response);

        } catch (final AgroSyncException excepcion) {
            var response = CuentaCobrarResponse.<ObtenerCuentaCobrarDTO>build(List.of(excepcion.getMensajeUsuario()), null);
            return GenerateResponse.generateBadRequestResponseWithData(response);
        } catch (final Exception excepcion) {
            var userMessage = "Error al consultar la Cuenta por Cobrar";
            var response = CuentaCobrarResponse.<ObtenerCuentaCobrarDTO>build(List.of(userMessage), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}