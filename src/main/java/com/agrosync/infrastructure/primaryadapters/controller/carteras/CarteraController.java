package com.agrosync.infrastructure.primaryadapters.controller.carteras;

import com.agrosync.application.primaryports.dto.carteras.request.CarteraPageDTO;
import com.agrosync.application.primaryports.dto.carteras.response.ObtenerCarteraDTO;
import com.agrosync.application.primaryports.dto.compras.response.ObtenerCompraDTO;
import com.agrosync.application.primaryports.interactor.carteras.ObtenerCarterasInteractor;
import com.agrosync.crosscutting.exception.custom.AgroSyncException;
import com.agrosync.infrastructure.primaryadapters.adapter.response.GenerateResponse;
import com.agrosync.infrastructure.primaryadapters.adapter.response.PageResponse;
import com.agrosync.infrastructure.primaryadapters.adapter.response.carteras.CarteraResponse;
import com.agrosync.infrastructure.primaryadapters.adapter.response.compras.CompraResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cartera")
public class CarteraController {

    private final ObtenerCarterasInteractor obtenerCarterasInteractor;

    public CarteraController(ObtenerCarterasInteractor obtenerCarterasInteractor) {
        this.obtenerCarterasInteractor = obtenerCarterasInteractor;
    }

    @GetMapping
    public ResponseEntity<CarteraResponse<PageResponse<ObtenerCarteraDTO>>> consultarCarteras(@RequestParam(defaultValue = "0") int page,
                                                                                             @RequestParam(defaultValue = "10") int size,
                                                                                             @RequestParam(defaultValue = "saldoActual") String sortBy,
                                                                                             @RequestParam(defaultValue = "DESC") String sortDirection,
                                                                                             @RequestParam(required = false) UUID usuarioId,
                                                                                             @RequestHeader(value = "x-suscripcion-id", required = false) UUID suscripcionId) {
        try {
            CarteraPageDTO request = CarteraPageDTO.create(page, size, sortBy, sortDirection, usuarioId, suscripcionId);
            PageResponse<ObtenerCarteraDTO> resultados = obtenerCarterasInteractor.ejecutar(request);

            CarteraResponse<PageResponse<ObtenerCarteraDTO>> response = CarteraResponse.build(List.of("Carteras consultadas correctamente"), resultados);
            return GenerateResponse.generateSuccessResponseWithData(response);
        } catch (final AgroSyncException excepcion) {
            var response = CarteraResponse.build(List.of(excepcion.getMensajeUsuario()), PageResponse.from(Page.<ObtenerCarteraDTO>empty()));
            return GenerateResponse.generateBadRequestResponseWithData(response);
        } catch (final IllegalArgumentException excepcion) {
            var response = CarteraResponse.build(List.of(excepcion.getMessage()), PageResponse.from(Page.<ObtenerCarteraDTO>empty()));
            return GenerateResponse.generateBadRequestResponseWithData(response);
        } catch (final Exception excepcion) {
            excepcion.printStackTrace();
            var response = CarteraResponse.build(List.of("Error al consultar las Carteras"), PageResponse.from(Page.<ObtenerCarteraDTO>empty()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
