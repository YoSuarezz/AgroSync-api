package com.agrosync.infrastructure.primaryadapters.controller.auth;

import com.agrosync.application.primaryports.dto.auth.LoginRequest;
import com.agrosync.application.primaryports.dto.auth.RegisterRequest;
import com.agrosync.application.primaryports.dto.auth.ActualizarEstadoUsuarioRequest;
import com.agrosync.application.primaryports.interactor.auth.LoginInteractor;
import com.agrosync.application.primaryports.interactor.auth.ObtenerUsuarioAutenticadoInteractor;
import com.agrosync.application.primaryports.interactor.auth.RegisterInteractor;
import com.agrosync.crosscutting.exception.custom.AgroSyncException;
import com.agrosync.infrastructure.primaryadapters.adapter.response.auth.AuthResponse;
import com.agrosync.infrastructure.primaryadapters.adapter.response.auth.AuthUserInfoResponse;
import com.agrosync.infrastructure.primaryadapters.adapter.response.GenericResponse;
import com.agrosync.infrastructure.secondaryadapters.auth.service.AuthUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final LoginInteractor loginInteractor;
    private final RegisterInteractor registerInteractor;
    private final ObtenerUsuarioAutenticadoInteractor obtenerUsuarioAutenticadoInteractor;
    private final AuthUserDetailsService authUserDetailsService;

    public AuthController(LoginInteractor loginInteractor, RegisterInteractor registerInteractor, ObtenerUsuarioAutenticadoInteractor obtenerUsuarioAutenticadoInteractor, AuthUserDetailsService authUserDetailsService) {
        this.loginInteractor = loginInteractor;
        this.registerInteractor = registerInteractor;
        this.obtenerUsuarioAutenticadoInteractor = obtenerUsuarioAutenticadoInteractor;
        this.authUserDetailsService = authUserDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        HttpStatus status = HttpStatus.ACCEPTED;
        AuthResponse response;
        try {
            response = loginInteractor.ejecutar(loginRequest);
            response.getMensajes().add("Usuario autenticado correctamente");
        } catch (AgroSyncException e) {
            status = HttpStatus.BAD_REQUEST;
            response = new AuthResponse();
            response.getMensajes().add(e.getMensajeUsuario());
        } catch (Exception e) {
            status = HttpStatus.UNAUTHORIZED;
            response = new AuthResponse();
            response.getMensajes().add(e.getMessage() != null ? e.getMessage() : "Credenciales incorrectas");
        }
        return new ResponseEntity<>(response, status);
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('SUPER_ADMINISTRADOR')")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {
        logger.info("Register request received: {}", registerRequest);
        HttpStatus status = HttpStatus.ACCEPTED;
        AuthResponse response;
        try {
            if (registerRequest.getRol() != null && registerRequest.getRol().name().equals("SUPER_ADMINISTRADOR")) {
                registerRequest.setSuscripcionId(null);
            } else if (registerRequest.getSuscripcionId() == null) {
                throw new IllegalArgumentException("El id de la suscripción es obligatorio para registrar usuarios");
            }
            response = registerInteractor.ejecutar(registerRequest);
            response.getMensajes().add("Usuario creado correctamente");
        } catch (AgroSyncException e) {
            status = HttpStatus.BAD_REQUEST;
            response = new AuthResponse();
            response.getMensajes().add(e.getMensajeUsuario());
        } catch (IllegalArgumentException e) {
            status = HttpStatus.BAD_REQUEST;
            response = new AuthResponse();
            response.getMensajes().add(e.getMessage());
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            response = new AuthResponse();
            response.getMensajes().add("Error al registrar el usuario");
        }
        return new ResponseEntity<>(response, status);
    }

    @PatchMapping("/users/{id}/estado")
    @PreAuthorize("hasRole('SUPER_ADMINISTRADOR')")
    public ResponseEntity<GenericResponse> actualizarEstadoUsuario(@PathVariable UUID id,
                                                                   @RequestBody ActualizarEstadoUsuarioRequest request) {
        HttpStatus status = HttpStatus.ACCEPTED;
        GenericResponse response;
        try {
            authUserDetailsService.actualizarEstadoUsuario(id, request.isActivo());
            response = GenericResponse.build(List.of("Estado de usuario actualizado"));
        } catch (Exception e) {
            status = HttpStatus.BAD_REQUEST;
            response = GenericResponse.build(List.of(e.getMessage()));
        }
        return new ResponseEntity<>(response, status);
    }

    @GetMapping("/me")
    public ResponseEntity<AuthUserInfoResponse> me() {
        HttpStatus status = HttpStatus.ACCEPTED;
        AuthUserInfoResponse response;
        try {
            response = obtenerUsuarioAutenticadoInteractor.ejecutar();
        } catch (Exception e) {


            status = HttpStatus.UNAUTHORIZED;
            response = new AuthUserInfoResponse();
            response.getMensajes().add("No se pudo obtener el usuario autenticado");
        }
        return new ResponseEntity<>(response, status);
    }
}
