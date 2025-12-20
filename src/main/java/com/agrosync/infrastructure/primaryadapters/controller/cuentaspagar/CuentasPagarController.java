package com.agrosync.infrastructure.primaryadapters.controller.cuentaspagar;

import com.agrosync.application.primaryports.dto.abonos.request.AbonoIdSuscripcionDTO;
import com.agrosync.application.primaryports.dto.abonos.request.AbonoPageDTO;
import com.agrosync.application.primaryports.dto.abonos.request.AnularAbonoDTO;
import com.agrosync.application.primaryports.dto.abonos.request.RegistrarAbonoDTO;
import com.agrosync.application.primaryports.dto.abonos.response.ObtenerAbonoDTO;
import com.agrosync.application.primaryports.dto.cuentaspagar.request.CuentaPagarIdSuscripcionDTO;
import com.agrosync.application.primaryports.dto.cuentaspagar.request.CuentaPagarPageDTO;
import com.agrosync.application.primaryports.dto.cuentaspagar.response.ObtenerCuentaPagarDTO;
import com.agrosync.application.primaryports.dto.usuarios.response.ObtenerUsuarioDTO;
import com.agrosync.domain.enums.cuentas.EstadoCuentaEnum;
import com.agrosync.domain.enums.cuentas.MetodoPagoEnum;
import com.agrosync.application.primaryports.interactor.abonos.AnularAbonoInteractor;
import com.agrosync.application.primaryports.interactor.abonos.ObtenerAbonoPorIdInteractor;
import com.agrosync.application.primaryports.interactor.abonos.ObtenerAbonosInteractor;
import com.agrosync.application.primaryports.interactor.abonos.ObtenerAbonosPorCuentaPagarInteractor;
import com.agrosync.application.primaryports.interactor.abonos.RegistrarNuevoAbonoInteractor;
import com.agrosync.application.primaryports.interactor.cuentaspagar.ObtenerCuentaPagarPorIdInteractor;
import com.agrosync.application.primaryports.interactor.cuentaspagar.ObtenerCuentasPagarInteractor;
import com.agrosync.crosscutting.exception.custom.AgroSyncException;
import com.agrosync.infrastructure.primaryadapters.adapter.response.GenerateResponse;
import com.agrosync.infrastructure.primaryadapters.adapter.response.GenericResponse;
import com.agrosync.infrastructure.primaryadapters.adapter.response.PageResponse;
import com.agrosync.infrastructure.primaryadapters.adapter.response.abonos.AbonoResponse;
import com.agrosync.infrastructure.primaryadapters.adapter.response.cuentaspagar.CuentaPagarResponse;
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
    private final RegistrarNuevoAbonoInteractor registrarNuevoAbonoInteractor;
    private final AnularAbonoInteractor anularAbonoInteractor;
    private final ObtenerAbonosPorCuentaPagarInteractor obtenerAbonosPorCuentaPagarInteractor;
    private final ObtenerAbonoPorIdInteractor obtenerAbonoPorIdInteractor;
    private final ObtenerAbonosInteractor obtenerAbonosInteractor;

    public CuentasPagarController(ObtenerCuentasPagarInteractor obtenerCuentasPagarInteractor,
            ObtenerCuentaPagarPorIdInteractor obtenerCuentaPagarPorIdInteractor,
            RegistrarNuevoAbonoInteractor registrarNuevoAbonoInteractor,
            AnularAbonoInteractor anularAbonoInteractor,
            ObtenerAbonosPorCuentaPagarInteractor obtenerAbonosPorCuentaPagarInteractor,
            ObtenerAbonoPorIdInteractor obtenerAbonoPorIdInteractor,
            ObtenerAbonosInteractor obtenerAbonosInteractor) {
        this.obtenerCuentasPagarInteractor = obtenerCuentasPagarInteractor;
        this.obtenerCuentaPagarPorIdInteractor = obtenerCuentaPagarPorIdInteractor;
        this.registrarNuevoAbonoInteractor = registrarNuevoAbonoInteractor;
        this.anularAbonoInteractor = anularAbonoInteractor;
        this.obtenerAbonosPorCuentaPagarInteractor = obtenerAbonosPorCuentaPagarInteractor;
        this.obtenerAbonoPorIdInteractor = obtenerAbonoPorIdInteractor;
        this.obtenerAbonosInteractor = obtenerAbonosInteractor;
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
            @RequestParam(required = false, name = "soloConSaldoPendiente") Boolean soloConSaldoPendiente,
            @RequestHeader(value = "x-suscripcion-id", required = false) UUID suscripcionId) {

        try {
            ObtenerUsuarioDTO proveedorFiltro = proveedorId != null
                    ? ObtenerUsuarioDTO.create(proveedorId, null, null, null)
                    : null;

            ObtenerCuentaPagarDTO filtro = ObtenerCuentaPagarDTO.create(
                    null,
                    numeroCuenta,
                    proveedorFiltro,
                    null,
                    null,
                    null,
                    null,
                    null);

            CuentaPagarPageDTO request = new CuentaPagarPageDTO(page, size, sortBy, sortDirection, filtro, estado,
                    suscripcionId);
            request.setSoloConSaldoPendiente(soloConSaldoPendiente);

            PageResponse<ObtenerCuentaPagarDTO> resultado = obtenerCuentasPagarInteractor.ejecutar(request);

            var response = CuentaPagarResponse.build(List.of("Consulta de cuentas por pagar exitosa"), resultado);
            return GenerateResponse.generateSuccessResponseWithData(response);

        } catch (final AgroSyncException excepcion) {
            var response = CuentaPagarResponse.build(List.of(excepcion.getMensajeUsuario()),
                    PageResponse.from(Page.<ObtenerCuentaPagarDTO>empty()));
            return GenerateResponse.generateBadRequestResponseWithData(response);
        } catch (final IllegalArgumentException excepcion) {
            var response = CuentaPagarResponse.build(List.of(excepcion.getMessage()),
                    PageResponse.from(Page.<ObtenerCuentaPagarDTO>empty()));
            return GenerateResponse.generateBadRequestResponseWithData(response);
        } catch (final Exception excepcion) {
            var response = CuentaPagarResponse.build(List.of("Error al consultar las Cuentas por Pagar"),
                    PageResponse.from(Page.<ObtenerCuentaPagarDTO>empty()));
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
            var response = CuentaPagarResponse.<ObtenerCuentaPagarDTO>build(List.of(excepcion.getMensajeUsuario()),
                    null);
            return GenerateResponse.generateBadRequestResponseWithData(response);
        } catch (final Exception excepcion) {
            var userMessage = "Error al consultar la Cuenta por Pagar";
            var response = CuentaPagarResponse.<ObtenerCuentaPagarDTO>build(List.of(userMessage), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/abonos")
    public ResponseEntity<AbonoResponse<List<ObtenerAbonoDTO>>> consultarAbonosPorCuentaPagar(
            @PathVariable UUID id,
            @RequestHeader(value = "x-suscripcion-id") UUID suscripcionId) {

        try {
            AbonoIdSuscripcionDTO data = AbonoIdSuscripcionDTO.create(id, suscripcionId);
            List<ObtenerAbonoDTO> abonos = obtenerAbonosPorCuentaPagarInteractor.ejecutar(data);

            var response = AbonoResponse.build(List.of("Consulta de abonos exitosa"), abonos);
            return GenerateResponse.generateSuccessResponseWithData(response);

        } catch (final AgroSyncException excepcion) {
            var response = AbonoResponse.<List<ObtenerAbonoDTO>>build(List.of(excepcion.getMensajeUsuario()), null);
            return GenerateResponse.generateBadRequestResponseWithData(response);
        } catch (final Exception excepcion) {
            var userMessage = "Error al consultar los abonos";
            var response = AbonoResponse.<List<ObtenerAbonoDTO>>build(List.of(userMessage), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/abonos")
    public ResponseEntity<AbonoResponse<PageResponse<ObtenerAbonoDTO>>> consultarAbonos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "fechaPago") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection,
            @RequestParam(required = false, name = "cuentaPagarId") UUID cuentaPagarId,
            @RequestParam(required = false, name = "metodoPago") MetodoPagoEnum metodoPago,
            @RequestHeader(value = "x-suscripcion-id", required = false) UUID suscripcionId) {

        try {
            AbonoPageDTO request = new AbonoPageDTO(page, size, sortBy, sortDirection, null, metodoPago, cuentaPagarId,
                    suscripcionId);

            PageResponse<ObtenerAbonoDTO> resultado = obtenerAbonosInteractor.ejecutar(request);

            var response = AbonoResponse.build(List.of("Consulta de abonos exitosa"), resultado);
            return GenerateResponse.generateSuccessResponseWithData(response);

        } catch (final AgroSyncException excepcion) {
            var response = AbonoResponse.build(List.of(excepcion.getMensajeUsuario()),
                    PageResponse.from(Page.<ObtenerAbonoDTO>empty()));
            return GenerateResponse.generateBadRequestResponseWithData(response);
        } catch (final IllegalArgumentException excepcion) {
            var response = AbonoResponse.build(List.of(excepcion.getMessage()),
                    PageResponse.from(Page.<ObtenerAbonoDTO>empty()));
            return GenerateResponse.generateBadRequestResponseWithData(response);
        } catch (final Exception excepcion) {
            var response = AbonoResponse.build(List.of("Error al consultar los abonos"),
                    PageResponse.from(Page.<ObtenerAbonoDTO>empty()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{cuentaId}/abonos/{abonoId}")
    public ResponseEntity<AbonoResponse<ObtenerAbonoDTO>> consultarAbonoPorId(
            @PathVariable UUID cuentaId,
            @PathVariable UUID abonoId,
            @RequestHeader(value = "x-suscripcion-id") UUID suscripcionId) {

        try {
            AbonoIdSuscripcionDTO data = AbonoIdSuscripcionDTO.create(abonoId, suscripcionId);
            ObtenerAbonoDTO abono = obtenerAbonoPorIdInteractor.ejecutar(data);

            var response = AbonoResponse.build(List.of("Abono consultado correctamente"), abono);
            return GenerateResponse.generateSuccessResponseWithData(response);

        } catch (final AgroSyncException excepcion) {
            var response = AbonoResponse.<ObtenerAbonoDTO>build(List.of(excepcion.getMensajeUsuario()), null);
            return GenerateResponse.generateBadRequestResponseWithData(response);
        } catch (final Exception excepcion) {
            var userMessage = "Error al consultar el abono";
            var response = AbonoResponse.<ObtenerAbonoDTO>build(List.of(userMessage), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}/abonos")
    public ResponseEntity<GenericResponse> registrarAbono(
            @PathVariable UUID id,
            @RequestBody RegistrarAbonoDTO abonoDTO,
            @RequestHeader(value = "x-suscripcion-id") UUID suscripcionId) {

        try {
            abonoDTO.setIdCuentaPagar(id);
            abonoDTO.setSuscripcionId(suscripcionId);

            registrarNuevoAbonoInteractor.ejecutar(abonoDTO);

            return GenerateResponse.generateSuccessResponse(
                    List.of("Abono registrado correctamente"));

        } catch (final AgroSyncException excepcion) {
            return GenerateResponse.generateBadRequestResponse(
                    List.of(excepcion.getMensajeUsuario()));

        } catch (final Exception excepcion) {
            var userMessage = "Error al registrar el abono";
            return new ResponseEntity<>(
                    GenericResponse.build(List.of(userMessage)),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{cuentaId}/abonos/{abonoId}/anular")
    public ResponseEntity<GenericResponse> anularAbono(
            @PathVariable UUID cuentaId,
            @PathVariable UUID abonoId,
            @RequestBody AnularAbonoDTO anularAbonoDTO,
            @RequestHeader(value = "x-suscripcion-id") UUID suscripcionId) {

        try {
            anularAbonoDTO.setAbonoId(abonoId);
            anularAbonoDTO.setCuentaPagarId(cuentaId);
            anularAbonoDTO.setSuscripcionId(suscripcionId);

            anularAbonoInteractor.ejecutar(anularAbonoDTO);

            return GenerateResponse.generateSuccessResponse(
                    List.of("El abono ha sido anulado correctamente"));

        } catch (final AgroSyncException excepcion) {
            return GenerateResponse.generateBadRequestResponse(
                    List.of(excepcion.getMensajeUsuario()));

        } catch (final Exception excepcion) {
            var userMessage = "Error al anular el abono";
            return new ResponseEntity<>(
                    GenericResponse.build(List.of(userMessage)),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
