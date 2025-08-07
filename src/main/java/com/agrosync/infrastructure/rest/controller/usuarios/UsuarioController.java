package com.agrosync.infrastructure.rest.controller.usuarios;

import com.agrosync.application.primaryports.dto.usuarios.UsuarioDTO;
import com.agrosync.application.primaryports.interactor.usuarios.RegistrarNuevoUsuarioInteractor;
import com.agrosync.application.primaryports.mapper.UsuarioMapper;
import com.agrosync.crosscutting.exception.custom.AgroSyncException;
import com.agrosync.domain.service.UsuarioService;
import com.agrosync.domain.usuarios.UsuarioDomain;
import com.agrosync.infrastructure.adapter.response.usuarios.UsuarioResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;
    private final RegistrarNuevoUsuarioInteractor registrarNuevoUsuarioInteractor;

    public UsuarioController(UsuarioService usuarioService, UsuarioMapper usuarioMapper, RegistrarNuevoUsuarioInteractor registrarNuevoUsuarioInteractor) {
        this.usuarioService = usuarioService;
        this.usuarioMapper = usuarioMapper;
        this.registrarNuevoUsuarioInteractor = registrarNuevoUsuarioInteractor;
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

    @PutMapping(path = "usuario")
    public ResponseEntity<UsuarioDTO> update(@RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDomain usuarioDomain = usuarioMapper.toDomain(usuarioDTO);
        UsuarioDomain usuarioSaved = usuarioService.save(usuarioDomain);
        UsuarioDTO responseDTO = usuarioMapper.toDTO(usuarioSaved);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping(path = "usuario/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        usuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "usuario/{id}")
    public ResponseEntity<UsuarioDTO> findById(@PathVariable Long id) {
        UsuarioDomain usuarioDomain = usuarioService.findById(id);
        if (usuarioDomain == null) {
            return ResponseEntity.notFound().build();
        }
        UsuarioDTO responseDTO = usuarioMapper.toDTO(usuarioDomain);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping(path = "usuarios")
    public ResponseEntity<List<UsuarioDTO>> findAll() {
        List<UsuarioDomain> usuarioDomains = usuarioService.findAll();
        List<UsuarioDTO> responseDTOs = usuarioDomains.stream()
                .map(usuarioMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }
}