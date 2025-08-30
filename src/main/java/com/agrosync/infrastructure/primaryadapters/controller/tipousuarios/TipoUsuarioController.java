package com.agrosync.infrastructure.primaryadapters.controller.tipousuarios;

import com.agrosync.application.primaryports.interactor.tipousuarios.ObtenerTipoUsuarioInteractor;
import com.agrosync.crosscutting.exception.custom.AgroSyncException;
import com.agrosync.infrastructure.primaryadapters.adapter.response.usuarios.UsuarioResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tipousuario")
public class TipoUsuarioController {

    private final ObtenerTipoUsuarioInteractor obtenerTipoUsuarioInteractor;

    public TipoUsuarioController(ObtenerTipoUsuarioInteractor obtenerTipoUsuarioInteractor) {
        this.obtenerTipoUsuarioInteractor = obtenerTipoUsuarioInteractor;
    }

    @GetMapping
    public ResponseEntity<UsuarioResponse> consultarTipoUsuario() {

        var httpStatusCode = HttpStatus.ACCEPTED;
        var usuarioResponse = new UsuarioResponse();
        try {
            usuarioResponse.setDatos(obtenerTipoUsuarioInteractor.ejecutar());
            usuarioResponse.getMensajes().add("Tipos de usuarios consultado correctamente");

        } catch (final AgroSyncException excepcion) {
            httpStatusCode = HttpStatus.BAD_REQUEST;
            usuarioResponse.getMensajes().add(excepcion.getMessage());
        } catch (final Exception excepcion) {
            httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR;
            var userMessage = "Error al consultar los tipos de usuario";
            usuarioResponse.getMensajes().add(userMessage);
        }

        return new ResponseEntity<>(usuarioResponse, httpStatusCode);
    }
}
