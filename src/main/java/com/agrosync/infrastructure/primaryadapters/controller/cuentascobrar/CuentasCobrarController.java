package com.agrosync.infrastructure.primaryadapters.controller.cuentascobrar;

import com.agrosync.application.primaryports.dto.cobros.request.CobroIdSuscripcionDTO;
import com.agrosync.application.primaryports.dto.cobros.request.CobroPageDTO;
import com.agrosync.application.primaryports.dto.cobros.request.RegistrarCobroDTO;
import com.agrosync.application.primaryports.dto.cobros.response.ObtenerCobroDTO;
import com.agrosync.application.primaryports.dto.cuentascobrar.request.CuentaCobrarIdSuscripcionDTO;
import com.agrosync.application.primaryports.dto.cuentascobrar.request.CuentaCobrarPageDTO;
import com.agrosync.application.primaryports.dto.cuentascobrar.response.ObtenerCuentaCobrarDTO;
import com.agrosync.application.primaryports.dto.usuarios.response.ObtenerUsuarioDTO;
import com.agrosync.application.primaryports.enums.cuentas.EstadoCuentaEnum;
import com.agrosync.application.primaryports.enums.cuentas.MetodoPagoEnum;
import com.agrosync.application.primaryports.interactor.cobros.ObtenerCobroPorIdInteractor;
import com.agrosync.application.primaryports.interactor.cobros.ObtenerCobrosInteractor;
import com.agrosync.application.primaryports.interactor.cobros.ObtenerCobrosPorCuentaCobrarInteractor;
import com.agrosync.application.primaryports.interactor.cobros.RegistrarNuevoCobroInteractor;
import com.agrosync.application.primaryports.interactor.cuentascobrar.ObtenerCuentaCobrarPorIdInteractor;
import com.agrosync.application.primaryports.interactor.cuentascobrar.ObtenerCuentasCobrarInteractor;
import com.agrosync.crosscutting.exception.custom.AgroSyncException;
import com.agrosync.infrastructure.primaryadapters.adapter.response.GenerateResponse;
import com.agrosync.infrastructure.primaryadapters.adapter.response.PageResponse;
import com.agrosync.infrastructure.primaryadapters.adapter.response.cobros.CobroResponse;
import com.agrosync.infrastructure.primaryadapters.adapter.response.cuentascobrar.CuentaCobrarResponse;
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
    private final RegistrarNuevoCobroInteractor registrarNuevoCobroInteractor;
    private final ObtenerCobrosPorCuentaCobrarInteractor obtenerCobrosPorCuentaCobrarInteractor;
    private final ObtenerCobroPorIdInteractor obtenerCobroPorIdInteractor;
    private final ObtenerCobrosInteractor obtenerCobrosInteractor;

    public CuentasCobrarController(ObtenerCuentasCobrarInteractor obtenerCuentasCobrarInteractor,
            ObtenerCuentaCobrarPorIdInteractor obtenerCuentaCobrarPorIdInteractor,
            RegistrarNuevoCobroInteractor registrarNuevoCobroInteractor,
            ObtenerCobrosPorCuentaCobrarInteractor obtenerCobrosPorCuentaCobrarInteractor,
            ObtenerCobroPorIdInteractor obtenerCobroPorIdInteractor,
            ObtenerCobrosInteractor obtenerCobrosInteractor) {
        this.obtenerCuentasCobrarInteractor = obtenerCuentasCobrarInteractor;
        this.obtenerCuentaCobrarPorIdInteractor = obtenerCuentaCobrarPorIdInteractor;
        this.registrarNuevoCobroInteractor = registrarNuevoCobroInteractor;
        this.obtenerCobrosPorCuentaCobrarInteractor = obtenerCobrosPorCuentaCobrarInteractor;
        this.obtenerCobroPorIdInteractor = obtenerCobroPorIdInteractor;
        this.obtenerCobrosInteractor = obtenerCobrosInteractor;
    }

    @GetMapping
    public ResponseEntity<CuentaCobrarResponse<PageResponse<ObtenerCuentaCobrarDTO>>> consultarCuentasCobrar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "fechaEmision") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection,
            @RequestParam(required = false) String numeroCuenta,
            @RequestParam(required = false, name = "clienteId") UUID clienteId,
            @RequestParam(required = false, name = "estado") EstadoCuentaEnum estado,
            @RequestHeader(value = "x-suscripcion-id", required = false) UUID suscripcionId) {

        try {
            ObtenerUsuarioDTO clienteFiltro = clienteId != null ? ObtenerUsuarioDTO.create(clienteId, null, null, null)
                    : null;

            ObtenerCuentaCobrarDTO filtro = ObtenerCuentaCobrarDTO.create(
                    null,
                    numeroCuenta,
                    clienteFiltro,
                    null,
                    null,
                    null,
                    null,
                    null);

            CuentaCobrarPageDTO request = new CuentaCobrarPageDTO(page, size, sortBy, sortDirection, filtro, estado,
                    suscripcionId);

            PageResponse<ObtenerCuentaCobrarDTO> resultado = obtenerCuentasCobrarInteractor.ejecutar(request);

            var response = CuentaCobrarResponse.build(List.of("Consulta de cuentas por cobrar exitosa"), resultado);
            return GenerateResponse.generateSuccessResponseWithData(response);

        } catch (final AgroSyncException excepcion) {
            var response = CuentaCobrarResponse.build(List.of(excepcion.getMensajeUsuario()),
                    PageResponse.from(Page.<ObtenerCuentaCobrarDTO>empty()));
            return GenerateResponse.generateBadRequestResponseWithData(response);
        } catch (final IllegalArgumentException excepcion) {
            var response = CuentaCobrarResponse.build(List.of(excepcion.getMessage()),
                    PageResponse.from(Page.<ObtenerCuentaCobrarDTO>empty()));
            return GenerateResponse.generateBadRequestResponseWithData(response);
        } catch (final Exception excepcion) {
            var response = CuentaCobrarResponse.build(List.of("Error al consultar las Cuentas por Cobrar"),
                    PageResponse.from(Page.<ObtenerCuentaCobrarDTO>empty()));
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

            var response = CuentaCobrarResponse.build(List.of("Cuenta por cobrar consultada correctamente"),
                    cuentaCobrar);
            return GenerateResponse.generateSuccessResponseWithData(response);

        } catch (final AgroSyncException excepcion) {
            var response = CuentaCobrarResponse.<ObtenerCuentaCobrarDTO>build(List.of(excepcion.getMensajeUsuario()),
                    null);
            return GenerateResponse.generateBadRequestResponseWithData(response);
        } catch (final Exception excepcion) {
            var userMessage = "Error al consultar la Cuenta por Cobrar";
            var response = CuentaCobrarResponse.<ObtenerCuentaCobrarDTO>build(List.of(userMessage), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/cobros")
    public ResponseEntity<CobroResponse<List<ObtenerCobroDTO>>> consultarCobrosPorCuentaCobrar(
            @PathVariable UUID id,
            @RequestHeader(value = "x-suscripcion-id") UUID suscripcionId) {

        try {
            CobroIdSuscripcionDTO data = CobroIdSuscripcionDTO.create(id, suscripcionId);
            List<ObtenerCobroDTO> cobros = obtenerCobrosPorCuentaCobrarInteractor.ejecutar(data);

            var response = CobroResponse.build(List.of("Consulta de cobros exitosa"), cobros);
            return GenerateResponse.generateSuccessResponseWithData(response);

        } catch (final AgroSyncException excepcion) {
            var response = CobroResponse.<List<ObtenerCobroDTO>>build(List.of(excepcion.getMensajeUsuario()), null);
            return GenerateResponse.generateBadRequestResponseWithData(response);
        } catch (final Exception excepcion) {
            var userMessage = "Error al consultar los cobros";
            var response = CobroResponse.<List<ObtenerCobroDTO>>build(List.of(userMessage), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/cobros")
    public ResponseEntity<CobroResponse<PageResponse<ObtenerCobroDTO>>> consultarCobros(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "fechaCobro") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection,
            @RequestParam(required = false, name = "cuentaCobrarId") UUID cuentaCobrarId,
            @RequestParam(required = false, name = "metodoPago") MetodoPagoEnum metodoPago,
            @RequestHeader(value = "x-suscripcion-id", required = false) UUID suscripcionId) {

        try {
            CobroPageDTO request = new CobroPageDTO(page, size, sortBy, sortDirection, null, metodoPago, cuentaCobrarId, suscripcionId);

            PageResponse<ObtenerCobroDTO> resultado = obtenerCobrosInteractor.ejecutar(request);

            var response = CobroResponse.build(List.of("Consulta de cobros exitosa"), resultado);
            return GenerateResponse.generateSuccessResponseWithData(response);

        } catch (final AgroSyncException excepcion) {
            var response = CobroResponse.build(List.of(excepcion.getMensajeUsuario()),
                    PageResponse.from(Page.<ObtenerCobroDTO>empty()));
            return GenerateResponse.generateBadRequestResponseWithData(response);
        } catch (final IllegalArgumentException excepcion) {
            var response = CobroResponse.build(List.of(excepcion.getMessage()),
                    PageResponse.from(Page.<ObtenerCobroDTO>empty()));
            return GenerateResponse.generateBadRequestResponseWithData(response);
        } catch (final Exception excepcion) {
            var response = CobroResponse.build(List.of("Error al consultar los cobros"),
                    PageResponse.from(Page.<ObtenerCobroDTO>empty()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{cuentaId}/cobros/{cobroId}")
    public ResponseEntity<CobroResponse<ObtenerCobroDTO>> consultarCobroPorId(
            @PathVariable UUID cobroId,
            @RequestHeader(value = "x-suscripcion-id") UUID suscripcionId) {

        try {
            CobroIdSuscripcionDTO data = CobroIdSuscripcionDTO.create(cobroId, suscripcionId);
            ObtenerCobroDTO cobro = obtenerCobroPorIdInteractor.ejecutar(data);

            var response = CobroResponse.build(List.of("Cobro consultado correctamente"), cobro);
            return GenerateResponse.generateSuccessResponseWithData(response);

        } catch (final AgroSyncException excepcion) {
            var response = CobroResponse.<ObtenerCobroDTO>build(List.of(excepcion.getMensajeUsuario()), null);
            return GenerateResponse.generateBadRequestResponseWithData(response);
        } catch (final Exception excepcion) {
            var userMessage = "Error al consultar el cobro";
            var response = CobroResponse.<ObtenerCobroDTO>build(List.of(userMessage), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}/cobros")
    public ResponseEntity<CobroResponse<Void>> registrarCobro(
            @PathVariable UUID id,
            @RequestBody RegistrarCobroDTO cobroDTO,
            @RequestHeader(value = "x-suscripcion-id") UUID suscripcionId) {

        try {
            cobroDTO.setIdCuentaCobrar(id);
            cobroDTO.setSuscripcionId(suscripcionId);

            registrarNuevoCobroInteractor.ejecutar(cobroDTO);

            var response = CobroResponse.<Void>build(List.of("Cobro registrado correctamente"), null);
            return GenerateResponse.generateSuccessResponseWithData(response);

        } catch (final AgroSyncException excepcion) {
            var response = CobroResponse.<Void>build(List.of(excepcion.getMensajeUsuario()), null);
            return GenerateResponse.generateBadRequestResponseWithData(response);
        } catch (final Exception excepcion) {
            var userMessage = "Error al registrar el cobro";
            var response = CobroResponse.<Void>build(List.of(userMessage), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
