package com.agrosync.infrastructure.primaryadapters.controller.carteras;

import com.agrosync.application.primaryports.dto.carteras.request.CarteraIdSuscripcionDTO;
import com.agrosync.application.primaryports.dto.carteras.request.CarteraPageDTO;
import com.agrosync.application.primaryports.dto.carteras.response.ObtenerCarteraDTO;
import com.agrosync.application.primaryports.dto.usuarios.response.ObtenerUsuarioDTO;
import com.agrosync.domain.enums.usuarios.TipoUsuarioEnum;
import com.agrosync.application.primaryports.interactor.carteras.ObtenerCarteraPorIdInteractor;
import com.agrosync.application.primaryports.interactor.carteras.ObtenerCarterasInteractor;
import com.agrosync.crosscutting.exception.custom.AgroSyncException;
import com.agrosync.infrastructure.primaryadapters.adapter.response.GenerateResponse;
import com.agrosync.infrastructure.primaryadapters.adapter.response.PageResponse;
import com.agrosync.infrastructure.primaryadapters.adapter.response.carteras.CarteraResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cartera")
public class CarteraController {

    private final ObtenerCarterasInteractor obtenerCarterasInteractor;
    private final ObtenerCarteraPorIdInteractor obtenerCarteraPorIdInteractor;

    public CarteraController(ObtenerCarterasInteractor obtenerCarterasInteractor, ObtenerCarteraPorIdInteractor obtenerCarteraPorIdInteractor) {
        this.obtenerCarterasInteractor = obtenerCarterasInteractor;
        this.obtenerCarteraPorIdInteractor = obtenerCarteraPorIdInteractor;
    }

    @GetMapping
    public ResponseEntity<CarteraResponse<PageResponse<ObtenerCarteraDTO>>> consultarCarteras(@RequestParam(defaultValue = "0") int page,
                                                                                             @RequestParam(defaultValue = "10") int size,
                                                                                             @RequestParam(defaultValue = "usuario.nombre") String sortBy,
                                                                                             @RequestParam(defaultValue = "ASC") String sortDirection,
                                                                                             @RequestParam(required = false) String nombreUsuario,
                                                                                             @RequestParam(required = false) String tipoUsuario,
                                                                                             @RequestHeader(value = "x-suscripcion-id", required = false) UUID suscripcionId) {
        try {
            ObtenerUsuarioDTO filtroUsuario = nombreUsuario != null ? ObtenerUsuarioDTO.create(null, nombreUsuario, null, null) : null;

            // Convertir String a TipoUsuarioEnum si no es null
            TipoUsuarioEnum tipoUsuarioEnum = null;
            if (tipoUsuario != null && !tipoUsuario.trim().isEmpty() && !"null".equals(tipoUsuario)) {
                try {
                    tipoUsuarioEnum = TipoUsuarioEnum.valueOf(tipoUsuario.toUpperCase());
                } catch (IllegalArgumentException e) {
                    // Si el valor no es válido, ignorar el filtro
                    tipoUsuarioEnum = null;
                }
            }

            CarteraPageDTO request = CarteraPageDTO.create(page, size, sortBy, sortDirection, filtroUsuario, suscripcionId, tipoUsuarioEnum);
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
            var response = CarteraResponse.build(List.of("Error al consultar las Carteras"), PageResponse.from(Page.<ObtenerCarteraDTO>empty()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarteraResponse<ObtenerCarteraDTO>> consultarCarteraPorId(@PathVariable UUID id,
                                                                                    @RequestHeader(value = "x-suscripcion-id", required = false) UUID suscripcionId) {

        try {
            CarteraIdSuscripcionDTO request = CarteraIdSuscripcionDTO.create(id, suscripcionId);
            ObtenerCarteraDTO cartera = obtenerCarteraPorIdInteractor.ejecutar(request);
            var carteraResponse = CarteraResponse.build(List.of("Cartera consultado correctamente"), cartera);
            return GenerateResponse.generateSuccessResponseWithData(carteraResponse);

        } catch (final AgroSyncException excepcion) {
            var carteraResponse = CarteraResponse.<ObtenerCarteraDTO>build(List.of(excepcion.getMensajeUsuario()), null);
            return GenerateResponse.generateBadRequestResponseWithData(carteraResponse);
        } catch (final Exception excepcion) {
            var userMessage = "Error al consultar la Cartera";
            var carteraResponse = CarteraResponse.<ObtenerCarteraDTO>build(List.of(userMessage), null);
            return new ResponseEntity<>(carteraResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
