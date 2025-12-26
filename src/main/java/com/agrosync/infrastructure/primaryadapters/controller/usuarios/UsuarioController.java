package com.agrosync.infrastructure.primaryadapters.controller.usuarios;

import com.agrosync.application.primaryports.dto.usuarios.request.*;
import com.agrosync.application.primaryports.dto.usuarios.response.ObtenerUsuarioDTO;
import com.agrosync.application.primaryports.dto.usuarios.response.ObtenerUsuarioDetalladoDTO;
import com.agrosync.application.primaryports.interactor.usuarios.*;
import com.agrosync.application.usecase.usuarios.ObtenerUsuarioDetallado;
import com.agrosync.domain.enums.usuarios.TipoUsuarioEnum;
import com.agrosync.crosscutting.exception.custom.AgroSyncException;
import com.agrosync.infrastructure.primaryadapters.adapter.response.GenerateResponse;
import com.agrosync.infrastructure.primaryadapters.adapter.response.GenericResponse;
import com.agrosync.infrastructure.primaryadapters.adapter.response.PageResponse;
import com.agrosync.infrastructure.primaryadapters.adapter.response.usuarios.UsuarioResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final RegistrarNuevoUsuarioInteractor registrarNuevoUsuarioInteractor;
    private final ObtenerUsuariosInteractor obtenerUsuarioInteractor;
    private final ObtenerUsuarioPorIdInteractor obtenerUsuarioPorIdInteractor;
    private final ActualizarUsuarioInteractor actualizarUsuarioInteractor;
    private final ObtenerUsuarioDetalladoInteractor obtenerUsuarioDetalladoInteractor;

    public UsuarioController(RegistrarNuevoUsuarioInteractor registrarNuevoUsuarioInteractor, ObtenerUsuariosInteractor obtenerUsuarioInteractor, ObtenerUsuarioPorIdInteractor obtenerUsuarioPorIdInteractor, ActualizarUsuarioInteractor actualizarUsuarioInteractor, ObtenerUsuarioDetallado obtenerUsuarioDetallado, ObtenerUsuarioDetalladoInteractor obtenerUsuarioDetalladoInteractor) {
        this.registrarNuevoUsuarioInteractor = registrarNuevoUsuarioInteractor;
        this.obtenerUsuarioInteractor = obtenerUsuarioInteractor;
        this.obtenerUsuarioPorIdInteractor = obtenerUsuarioPorIdInteractor;
        this.actualizarUsuarioInteractor = actualizarUsuarioInteractor;
        this.obtenerUsuarioDetalladoInteractor = obtenerUsuarioDetalladoInteractor;
    }

    @PostMapping()
    public ResponseEntity<GenericResponse> registrarUsuario(@RequestBody RegistrarNuevoUsuarioDTO usuario,
                                                           @RequestHeader(value = "x-suscripcion-id", required = false) UUID suscripcionId) {
        try {
            usuario.setSuscripcionId(suscripcionId);
            registrarNuevoUsuarioInteractor.ejecutar(usuario);
            return GenerateResponse.generateSuccessResponse(List.of("Se ha registrado el usuario correctamente"));
        } catch (final AgroSyncException excepcion) {
            return GenerateResponse.generateBadRequestResponse(List.of(excepcion.getMensajeUsuario()));
        } catch (final Exception excepcion) {
            var userMessage = "Error al registrar el usuario";
            return new ResponseEntity<>(GenericResponse.build(List.of(userMessage)), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<UsuarioResponse<PageResponse<ObtenerUsuarioDTO>>> consultarUsuarios(@RequestParam(defaultValue = "0") int page,
                                                                                                     @RequestParam(defaultValue = "10") int size,
                                                                                                     @RequestParam(defaultValue = "nombre") String sortBy,
                                                                                                     @RequestParam(defaultValue = "ASC") String sortDirection,
                                                                                                     @RequestParam(required = false) String nombre,
                                                                                                     @RequestParam(required = false) String telefono,
                                                                                                     @RequestParam(required = false, name = "tipoUsuario") TipoUsuarioEnum tipoUsuario,
                                                                                                     @RequestHeader(value = "x-suscripcion-id", required = false) UUID suscripcionId) {

        try {
            ObtenerUsuarioDTO filtro = ObtenerUsuarioDTO.create(null, nombre, telefono, tipoUsuario);
            UsuarioPageDTO request = UsuarioPageDTO.create(page, size, sortBy, sortDirection, filtro, tipoUsuario, suscripcionId);

            PageResponse<ObtenerUsuarioDTO> resultados = obtenerUsuarioInteractor.ejecutar(request);
            UsuarioResponse<PageResponse<ObtenerUsuarioDTO>> response = UsuarioResponse.build(List.of("Usuarios consultados correctamente"), resultados);
            return GenerateResponse.generateSuccessResponseWithData(response);

        } catch (final AgroSyncException excepcion) {
            var response = UsuarioResponse.build(List.of(excepcion.getMensajeUsuario()), PageResponse.from(Page.<ObtenerUsuarioDTO>empty()));
            return GenerateResponse.generateBadRequestResponseWithData(response);
        } catch (final IllegalArgumentException excepcion) {
            var response = UsuarioResponse.build(List.of(excepcion.getMessage()), PageResponse.from(Page.<ObtenerUsuarioDTO>empty()));
            return GenerateResponse.generateBadRequestResponseWithData(response);
        } catch (final Exception excepcion) {
            var response = UsuarioResponse.build(List.of("Error al consultar los Usuarios"), PageResponse.from(Page.<ObtenerUsuarioDTO>empty()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse<ObtenerUsuarioDTO>> consultarUsuarioPorId(@PathVariable UUID id,
                                                                                   @RequestHeader(value = "x-suscripcion-id", required = false) UUID suscripcionId) {

        try {
            UsuarioIdSuscripcionDTO request = UsuarioIdSuscripcionDTO.create(id, suscripcionId);
            ObtenerUsuarioDTO usuario = obtenerUsuarioPorIdInteractor.ejecutar(request);
            var usuarioResponse = UsuarioResponse.build(List.of("Usuario consultado correctamente"), usuario);
            return GenerateResponse.generateSuccessResponseWithData(usuarioResponse);

        } catch (final AgroSyncException excepcion) {
            var usuarioResponse = UsuarioResponse.<ObtenerUsuarioDTO>build(List.of(excepcion.getMensajeUsuario()), null);
            return GenerateResponse.generateBadRequestResponseWithData(usuarioResponse);
        } catch (final Exception excepcion) {
            var userMessage = "Error al consultar el Usuario";
            var usuarioResponse = UsuarioResponse.<ObtenerUsuarioDTO>build(List.of(userMessage), null);
            return new ResponseEntity<>(usuarioResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericResponse> actualizarUsuario(@PathVariable UUID id, @RequestBody ActualizarUsuarioDTO usuario,
                                                             @RequestHeader(value = "x-suscripcion-id", required = false) UUID suscripcionId) {

        try {
            usuario.setId(id);
            usuario.setSuscripcionId(suscripcionId);
            actualizarUsuarioInteractor.ejecutar(usuario);
            return GenerateResponse.generateSuccessResponse(List.of("Usuario actualizado correctamente"));
        } catch (final AgroSyncException excepcion) {
            return GenerateResponse.generateBadRequestResponse(List.of(excepcion.getMensajeUsuario()));
        } catch (final Exception excepcion) {
            var mensajeUsuario = "Error al actualizar el Usuario";
            return new ResponseEntity<>(GenericResponse.build(List.of(mensajeUsuario)), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/detalle/{id}")
    public ResponseEntity<UsuarioResponse<ObtenerUsuarioDetalladoDTO>> consultarUsuarioDetallado(
            @PathVariable UUID id,
            @RequestHeader(value = "x-suscripcion-id", required = false) UUID suscripcionId,
            @RequestHeader LocalDate fechaInicio,
            @RequestHeader LocalDate fechaFin,
            @RequestHeader boolean saldoPendiente) {

        try {
            UsuarioDetalladoFiltroDTO request = UsuarioDetalladoFiltroDTO.create(fechaInicio, fechaFin, saldoPendiente, id, suscripcionId);
            ObtenerUsuarioDetalladoDTO usuario = obtenerUsuarioDetalladoInteractor.ejecutar(request);
            var usuarioResponse = UsuarioResponse.build(List.of("Detalles de usuario consultado correctamente"), usuario);
            return GenerateResponse.generateSuccessResponseWithData(usuarioResponse);

        } catch (final AgroSyncException excepcion) {
            var usuarioResponse = UsuarioResponse.<ObtenerUsuarioDetalladoDTO>build(List.of(excepcion.getMensajeUsuario()), null);
            return GenerateResponse.generateBadRequestResponseWithData(usuarioResponse);
        } catch (final Exception excepcion) {
            var userMessage = "Error al consultar el detalle de usuario";
            var usuarioResponse = UsuarioResponse.<ObtenerUsuarioDetalladoDTO>build(List.of(userMessage), null);
            return new ResponseEntity<>(usuarioResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
