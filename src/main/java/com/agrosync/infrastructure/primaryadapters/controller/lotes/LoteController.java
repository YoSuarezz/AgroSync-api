package com.agrosync.infrastructure.primaryadapters.controller.lotes;

import com.agrosync.application.primaryports.dto.lotes.request.LoteIdSuscripcionDTO;
import com.agrosync.application.primaryports.dto.lotes.request.LotePageDTO;
import com.agrosync.application.primaryports.dto.lotes.response.ObtenerLoteDTO;
import com.agrosync.application.primaryports.interactor.lotes.ObtenerLotePorIdInteractor;
import com.agrosync.application.primaryports.interactor.lotes.ObtenerLotesInteractor;
import com.agrosync.crosscutting.exception.custom.AgroSyncException;
import com.agrosync.infrastructure.primaryadapters.adapter.response.GenerateResponse;
import com.agrosync.infrastructure.primaryadapters.adapter.response.PageResponse;
import com.agrosync.infrastructure.primaryadapters.adapter.response.lotes.LoteResponse; // Clase asumida
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/lotes")
public class LoteController {

    private final ObtenerLotesInteractor obtenerLotesInteractor;
    private final ObtenerLotePorIdInteractor obtenerLotePorIdInteractor;

    public LoteController(ObtenerLotesInteractor obtenerLotesInteractor,
                          ObtenerLotePorIdInteractor obtenerLotePorIdInteractor) {
        this.obtenerLotesInteractor = obtenerLotesInteractor;
        this.obtenerLotePorIdInteractor = obtenerLotePorIdInteractor;
    }


    @GetMapping
    public ResponseEntity<LoteResponse<PageResponse<ObtenerLoteDTO>>> consultarLotes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "numeroLote") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection,
            @RequestParam(required = false) String numeroLote,
            @RequestParam(required = false) LocalDate fecha,
            @RequestParam(required = false) BigDecimal pesoTotal,
            @RequestHeader(value = "x-suscripcion-id", required = false) UUID suscripcionId) {

        try {
            ObtenerLoteDTO filtro = ObtenerLoteDTO.create(null, numeroLote, fecha, pesoTotal);
            LotePageDTO request = LotePageDTO.create(page, size, sortBy, sortDirection, filtro, suscripcionId);

            PageResponse<ObtenerLoteDTO> resultado = obtenerLotesInteractor.ejecutar(request);
            LoteResponse<PageResponse<ObtenerLoteDTO>> response = LoteResponse.build(
                    List.of("Consulta de lotes exitosa"),
                    resultado);
            return GenerateResponse.generateSuccessResponseWithData(response);

        } catch (final AgroSyncException excepcion) {
            var response = LoteResponse.build(List.of(excepcion.getMensajeUsuario()), PageResponse.from(Page.<ObtenerLoteDTO>empty()));
            return GenerateResponse.generateBadRequestResponseWithData(response);
        } catch (final IllegalArgumentException excepcion) {
            var response = LoteResponse.build(List.of(excepcion.getMessage()), PageResponse.from(Page.<ObtenerLoteDTO>empty()));
            return GenerateResponse.generateBadRequestResponseWithData(response);
        } catch (final Exception excepcion) {
            var response = LoteResponse.build(List.of("Error al consultar los Lotes"), PageResponse.from(Page.<ObtenerLoteDTO>empty()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // -------------------------------------------------------------------------------------------------------------

    @GetMapping("/{id}")
    public ResponseEntity<LoteResponse<ObtenerLoteDTO>> consultarLotePorId(@PathVariable UUID id,
                                                                           @RequestHeader(value = "x-suscripcion-id", required = false) UUID suscripcionId) {
        try {
            LoteIdSuscripcionDTO request = LoteIdSuscripcionDTO.create(id, suscripcionId);
            ObtenerLoteDTO lote = obtenerLotePorIdInteractor.ejecutar(request);

            var loteResponse = LoteResponse.build(List.of("Lote consultado correctamente"), lote);
            return GenerateResponse.generateSuccessResponseWithData(loteResponse);
        } catch (final AgroSyncException excepcion) {
            var loteResponse = LoteResponse.<ObtenerLoteDTO>build(List.of(excepcion.getMensajeUsuario()), null);
            return GenerateResponse.generateBadRequestResponseWithData(loteResponse);
        } catch (final Exception excepcion) {
            var userMessage = "Error al consultar el Lote";
            var loteResponse = LoteResponse.<ObtenerLoteDTO>build(List.of(userMessage), null);
            return new ResponseEntity<>(loteResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}