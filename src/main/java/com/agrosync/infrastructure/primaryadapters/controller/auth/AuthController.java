package com.agrosync.infrastructure.primaryadapters.controller.auth;

import com.agrosync.application.primaryports.dto.auth.LoginRequest;
import com.agrosync.application.primaryports.dto.auth.RegisterRequest;
import com.agrosync.application.primaryports.interactor.auth.LoginInteractor;
import com.agrosync.application.primaryports.interactor.auth.ObtenerUsuarioAutenticadoInteractor;
import com.agrosync.application.primaryports.interactor.auth.RegisterInteractor;
import com.agrosync.crosscutting.exception.custom.AgroSyncException;
import com.agrosync.infrastructure.primaryadapters.adapter.response.auth.AuthResponse;
import com.agrosync.infrastructure.primaryadapters.adapter.response.auth.AuthUserInfoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final LoginInteractor loginInteractor;
    private final RegisterInteractor registerInteractor;
    private final ObtenerUsuarioAutenticadoInteractor obtenerUsuarioAutenticadoInteractor;

    public AuthController(LoginInteractor loginInteractor, RegisterInteractor registerInteractor, ObtenerUsuarioAutenticadoInteractor obtenerUsuarioAutenticadoInteractor) {
        this.loginInteractor = loginInteractor;
        this.registerInteractor = registerInteractor;
        this.obtenerUsuarioAutenticadoInteractor = obtenerUsuarioAutenticadoInteractor;
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
            response.getMensajes().add("Credenciales incorrectas");
        }
        return new ResponseEntity<>(response, status);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest,
                                                 @RequestHeader(value = "x-subscription-id", required = false) UUID suscripcionId) {
        System.out.println("Register request received: " + registerRequest);
        HttpStatus status = HttpStatus.ACCEPTED;
        AuthResponse response;
        try {
            registerRequest.setSuscripcionId(suscripcionId);
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
