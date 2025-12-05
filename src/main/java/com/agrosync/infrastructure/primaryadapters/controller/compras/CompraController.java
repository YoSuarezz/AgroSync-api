package com.agrosync.infrastructure.primaryadapters.controller.compras;

import com.agrosync.application.primaryports.dto.compras.request.RegistrarNuevaCompraDTO;
import com.agrosync.application.primaryports.interactor.compras.RegistrarNuevaCompraInteractor;
import com.agrosync.crosscutting.exception.custom.AgroSyncException;
import com.agrosync.infrastructure.primaryadapters.adapter.response.GenerateResponse;
import com.agrosync.infrastructure.primaryadapters.adapter.response.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/compra")
public class CompraController {

    private final RegistrarNuevaCompraInteractor registrarNuevaCompraInteractor;

    public CompraController(RegistrarNuevaCompraInteractor registrarNuevaCompraInteractor) {
        this.registrarNuevaCompraInteractor = registrarNuevaCompraInteractor;
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
            excepcion.printStackTrace();
            var userMessage = "Error al registrar la compra";
            return new ResponseEntity<>(GenericResponse.build(List.of(userMessage)), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
