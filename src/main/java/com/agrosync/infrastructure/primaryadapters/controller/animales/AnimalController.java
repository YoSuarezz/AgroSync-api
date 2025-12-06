package com.agrosync.infrastructure.primaryadapters.controller.animales;

import com.agrosync.application.primaryports.dto.animales.request.AnimalIdSuscripcionDTO;
import com.agrosync.application.primaryports.dto.animales.request.AnimalPageDTO;
import com.agrosync.application.primaryports.dto.animales.response.ObtenerAnimalDTO;
import com.agrosync.application.primaryports.enums.animales.EstadoAnimalEnum;
import com.agrosync.application.primaryports.enums.animales.SexoEnum;
import com.agrosync.application.primaryports.interactor.animales.ObtenerAnimalPorIdInteractor;
import com.agrosync.application.primaryports.interactor.animales.ObtenerAnimalesInteractor;
import com.agrosync.crosscutting.exception.custom.AgroSyncException;
import com.agrosync.infrastructure.primaryadapters.adapter.response.GenerateResponse;
import com.agrosync.infrastructure.primaryadapters.adapter.response.PageResponse;
import com.agrosync.infrastructure.primaryadapters.adapter.response.animales.AnimalResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/animales")
public class AnimalController {

    private final ObtenerAnimalesInteractor obtenerAnimalesInteractor;
    private final ObtenerAnimalPorIdInteractor obtenerAnimalPorIdInteractor;

    public AnimalController(ObtenerAnimalesInteractor obtenerAnimalesInteractor,
                            ObtenerAnimalPorIdInteractor obtenerAnimalPorIdInteractor) {
        this.obtenerAnimalesInteractor = obtenerAnimalesInteractor;
        this.obtenerAnimalPorIdInteractor = obtenerAnimalPorIdInteractor;
    }



    @GetMapping
    public ResponseEntity<AnimalResponse<PageResponse<ObtenerAnimalDTO>>> consultarAnimales(@RequestParam(defaultValue = "0") int page,
                                                                                            @RequestParam(defaultValue = "10") int size,
                                                                                            @RequestParam(defaultValue = "nombre") String sortBy,
                                                                                            @RequestParam(defaultValue = "ASC") String sortDirection,
                                                                                            @RequestParam(required = false) String numeroAnimal,
                                                                                            @RequestParam(required = false, name = "sexo") SexoEnum sexo,
                                                                                            @RequestParam(required = false) BigDecimal peso,
                                                                                            @RequestParam(required = false, name = "estado") EstadoAnimalEnum estado,
                                                                                            @RequestHeader(value = "x-suscripcion-id", required = false) UUID suscripcionId) {
        try {
            ObtenerAnimalDTO filtro = ObtenerAnimalDTO.create(null, numeroAnimal, sexo, peso, estado);
            AnimalPageDTO request = AnimalPageDTO.create(page, size, sortBy, sortDirection, filtro, sexo, estado, suscripcionId);

            PageResponse<ObtenerAnimalDTO> resultado = obtenerAnimalesInteractor.ejecutar(request);
            AnimalResponse<PageResponse<ObtenerAnimalDTO>> response = AnimalResponse.build(List.of("Consulta de animales exitosa"), resultado);
            return GenerateResponse.generateSuccessResponseWithData(response);

        } catch (final AgroSyncException excepcion) {
            var response = AnimalResponse.build(List.of(excepcion.getMensajeUsuario()), PageResponse.from(Page.<ObtenerAnimalDTO>empty()));
            return GenerateResponse.generateBadRequestResponseWithData(response);
        } catch (final IllegalArgumentException excepcion) {
            var response = AnimalResponse.build(List.of(excepcion.getMessage()), PageResponse.from(Page.<ObtenerAnimalDTO>empty()));
            return GenerateResponse.generateBadRequestResponseWithData(response);
        } catch (final Exception excepcion) {
            var response = AnimalResponse.build(List.of("Error al consultar los Animales"), PageResponse.from(Page.<ObtenerAnimalDTO>empty()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimalResponse<ObtenerAnimalDTO>> consultarAnimalPorId(@PathVariable UUID id,
                                                                                    @RequestHeader(value = "x-suscripcion-id", required = false) UUID suscripcionId) {
        try {
            AnimalIdSuscripcionDTO request = AnimalIdSuscripcionDTO.create(id, suscripcionId);
            ObtenerAnimalDTO animal = obtenerAnimalPorIdInteractor.ejecutar(request);
            var animalResponse = AnimalResponse.build(List.of("Animal consultado correctamente"), animal);
            return GenerateResponse.generateSuccessResponseWithData(animalResponse);

        } catch (final AgroSyncException excepcion) {
            var animalResponse = AnimalResponse.<ObtenerAnimalDTO>build(List.of(excepcion.getMensajeUsuario()), null);
            return GenerateResponse.generateBadRequestResponseWithData(animalResponse);
        } catch (final Exception excepcion) {
            var userMessage = "Error al consultar el Animal";
            var animalResponse = AnimalResponse.<ObtenerAnimalDTO>build(List.of(userMessage), null);
            return new ResponseEntity<>(animalResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
