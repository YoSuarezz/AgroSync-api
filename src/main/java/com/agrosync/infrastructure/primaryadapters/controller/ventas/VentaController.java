package com.agrosync.infrastructure.primaryadapters.controller.ventas;

import com.agrosync.application.primaryports.dto.ventas.request.RegistrarNuevaVentaDTO;
import com.agrosync.application.primaryports.interactor.ventas.RegistrarNuevaVentaInteractor;
import com.agrosync.crosscutting.exception.custom.AgroSyncException;
import com.agrosync.infrastructure.primaryadapters.adapter.response.GenerateResponse;
import com.agrosync.infrastructure.primaryadapters.adapter.response.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/venta")
public class VentaController {

    private final RegistrarNuevaVentaInteractor registrarNuevaVentaInteractor;

    public VentaController(RegistrarNuevaVentaInteractor registrarNuevaVentaInteractor) {
        this.registrarNuevaVentaInteractor = registrarNuevaVentaInteractor;
    }

    @PostMapping
    public ResponseEntity<GenericResponse> registrarVenta(@RequestBody RegistrarNuevaVentaDTO venta,
                                                          @RequestHeader(value = "x-suscripcion-id", required = false) UUID suscripcionId) {
        try {
            venta.setSuscripcionId(suscripcionId);
            registrarNuevaVentaInteractor.ejecutar(venta);
            return GenerateResponse.generateSuccessResponse(List.of("Se ha registrado la venta correctamente"));
        } catch (final AgroSyncException excepcion) {
            return GenerateResponse.generateBadRequestResponse(List.of(excepcion.getMensajeUsuario()));
        } catch (final Exception excepcion) {
            excepcion.printStackTrace();
            var userMessage = "Error al registrar la venta";
            return new ResponseEntity<>(GenericResponse.build(List.of(userMessage)), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
