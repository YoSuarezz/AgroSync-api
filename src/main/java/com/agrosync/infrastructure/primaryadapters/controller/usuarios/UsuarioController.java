package com.agrosync.infrastructure.primaryadapters.controller.usuarios;

import com.agrosync.application.primaryports.dto.usuarios.UsuarioDTO;
import com.agrosync.application.primaryports.dto.usuarios.UsuarioRequest;
import com.agrosync.application.primaryports.interactor.usuarios.ActualizarUsuarioInteractor;
import com.agrosync.application.primaryports.interactor.usuarios.ObtenerUsuarioPorIdInteractor;
import com.agrosync.application.primaryports.interactor.usuarios.ObtenerUsuariosInteractor;
import com.agrosync.application.primaryports.interactor.usuarios.RegistrarNuevoUsuarioInteractor;
import com.agrosync.crosscutting.exception.custom.AgroSyncException;
import com.agrosync.domain.service.UsuarioService;
import com.agrosync.infrastructure.primaryadapters.adapter.response.usuarios.UsuarioResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final RegistrarNuevoUsuarioInteractor registrarNuevoUsuarioInteractor;
    private final ObtenerUsuariosInteractor obtenerUsuarioInteractor;
    private final ObtenerUsuarioPorIdInteractor obtenerUsuarioPorIdInteractor;
    private final ActualizarUsuarioInteractor actualizarUsuarioInteractor;

    public UsuarioController(UsuarioService usuarioService, RegistrarNuevoUsuarioInteractor registrarNuevoUsuarioInteractor, ObtenerUsuariosInteractor obtenerUsuarioInteractor, ObtenerUsuarioPorIdInteractor obtenerUsuarioPorIdInteractor, ActualizarUsuarioInteractor actualizarUsuarioInteractor) {
        this.usuarioService = usuarioService;
        this.registrarNuevoUsuarioInteractor = registrarNuevoUsuarioInteractor;
        this.obtenerUsuarioInteractor = obtenerUsuarioInteractor;
        this.obtenerUsuarioPorIdInteractor = obtenerUsuarioPorIdInteractor;
        this.actualizarUsuarioInteractor = actualizarUsuarioInteractor;
    }

    @PostMapping()
    public ResponseEntity<UsuarioResponse> registrarUsuario(@RequestBody UsuarioDTO usuario) {

        var httpStatusCode = HttpStatus.ACCEPTED;
        var usuarioResponse = new UsuarioResponse();

        try {
            registrarNuevoUsuarioInteractor.ejecutar(usuario);
            usuarioResponse.getMensajes().add("Se ha registrado el usuario correctamente");

        } catch (final AgroSyncException excepcion) {
            httpStatusCode = HttpStatus.BAD_REQUEST;
            usuarioResponse.getMensajes().add(excepcion.getMensajeUsuario());
        } catch (final Exception excepcion) {
            httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR;
            var userMessage = "Error al registrar el usuario";
            usuarioResponse.getMensajes().add(userMessage);
        }
        return new ResponseEntity<>(usuarioResponse, httpStatusCode);
    }

    @GetMapping
    public ResponseEntity<UsuarioResponse> consultarUsuarios(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int size,
                                                             @RequestParam(defaultValue = "nombre") String sortBy,
                                                             @RequestParam(defaultValue = "ASC") String sortDirection,
                                                             @RequestParam(required = false) String nombre) {
        var httpStatusCode = HttpStatus.ACCEPTED;
        var usuarioResponse = new UsuarioResponse();
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        try {
            UsuarioDTO usuarioDTO = UsuarioDTO.create().setNombre(nombre);
            var request = UsuarioRequest.create().setPageable(pageable).setUsuario(usuarioDTO);

            usuarioResponse.setDatos(obtenerUsuarioInteractor.ejecutar(request));
            usuarioResponse.getMensajes().add("Usuarios consultados correctamente");

        } catch (final AgroSyncException excepcion) {
            httpStatusCode = HttpStatus.BAD_REQUEST;
            usuarioResponse.getMensajes().add(excepcion.getMessage());
        } catch (final Exception excepcion) {
            httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR;
            usuarioResponse.getMensajes().add("Error al consultar los Usuarios");
        }
        return new ResponseEntity<>(usuarioResponse, httpStatusCode);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> consultarUsuarioPorId(@PathVariable Long id) {

        var httpStatusCode = HttpStatus.ACCEPTED;
        var usuarioResponse = new UsuarioResponse();
        try {
            usuarioResponse.setDatos(obtenerUsuarioPorIdInteractor.ejecutar(id));
            usuarioResponse.getMensajes().add("Usuario consultado correctamente");

        } catch (final AgroSyncException excepcion) {
            httpStatusCode = HttpStatus.BAD_REQUEST;
            usuarioResponse.getMensajes().add(excepcion.getMessage());
        } catch (final Exception excepcion) {
            httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR;
            var userMessage = "Error al consultar el Usuario";
            usuarioResponse.getMensajes().add(userMessage);
        }

        return new ResponseEntity<>(usuarioResponse, httpStatusCode);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> actualizarUsuario(@PathVariable Long id, @RequestBody UsuarioDTO usuario) {

        var httpStatusCode = HttpStatus.ACCEPTED;
        var usuarioResponse = new UsuarioResponse();

        try {
            usuario.setId(id);
            actualizarUsuarioInteractor.ejecutar(usuario);
            usuarioResponse.getMensajes().add("Usuario actualizado correctamente");
        } catch (final AgroSyncException excepcion) {
            httpStatusCode = HttpStatus.BAD_REQUEST;
            usuarioResponse.getMensajes().add(excepcion.getMessage());
        } catch (final Exception excepcion) {
            httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR;
            var mensajeUsuario = "Error al actualizar el Usuario";
            usuarioResponse.getMensajes().add(mensajeUsuario);
        }
        return new ResponseEntity<>(usuarioResponse, httpStatusCode);
    }

    @DeleteMapping(path = "usuario/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        usuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}