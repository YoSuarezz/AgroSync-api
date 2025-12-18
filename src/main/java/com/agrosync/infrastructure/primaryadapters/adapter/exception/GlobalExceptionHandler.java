package com.agrosync.infrastructure.primaryadapters.adapter.exception;

import com.agrosync.crosscutting.exception.custom.AgroSyncException;
import com.agrosync.crosscutting.exception.custom.BusinessAgroSyncException;
import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;
import com.agrosync.infrastructure.primaryadapters.adapter.response.GenericResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;


 //Manejador global de excepciones para la API REST.
 //Centraliza el manejo de excepciones y estandariza las respuestas de error.

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


     //Maneja excepciones de reglas de negocio.

    @ExceptionHandler(RuleAgroSyncException.class)
    public ResponseEntity<GenericResponse> handleRuleException(RuleAgroSyncException ex) {
        logger.warn("Regla de negocio violada: {}", ex.getMensajeUsuario());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(GenericResponse.build(List.of(ex.getMensajeUsuario())));
    }


     //Maneja excepciones de lógica de negocio.

    @ExceptionHandler(BusinessAgroSyncException.class)
    public ResponseEntity<GenericResponse> handleBusinessException(BusinessAgroSyncException ex) {
        logger.warn("Error de negocio: {}", ex.getMensajeUsuario());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(GenericResponse.build(List.of(ex.getMensajeUsuario())));
    }


     //Maneja excepciones generales de la aplicación.

    @ExceptionHandler(AgroSyncException.class)
    public ResponseEntity<GenericResponse> handleAgroSyncException(AgroSyncException ex) {
        logger.error("Error en la aplicación [{}]: {}", ex.getLayer(), ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(GenericResponse.build(List.of(ex.getMensajeUsuario())));
    }

     //Maneja errores cuando falta el header de suscripción.

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<GenericResponse> handleMissingHeader(MissingRequestHeaderException ex) {
        String mensaje = "Falta el encabezado requerido: " + ex.getHeaderName();
        if ("x-suscripcion-id".equalsIgnoreCase(ex.getHeaderName())) {
            mensaje = "El identificador de suscripción es requerido. Por favor, incluya el header 'x-suscripcion-id'.";
        }
        logger.warn("Header faltante: {}", ex.getHeaderName());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(GenericResponse.build(List.of(mensaje)));
    }


     //Maneja errores de conversión de tipos en parámetros.

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<GenericResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String mensaje = String.format("El parámetro '%s' tiene un formato inválido.", ex.getName());
        logger.warn("Error de tipo en parámetro {}: {}", ex.getName(), ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(GenericResponse.build(List.of(mensaje)));
    }

     //Maneja errores de validación de argumentos.

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GenericResponse> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errores = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        logger.warn("Errores de validación: {}", errores);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(GenericResponse.build(errores));
    }


     //Maneja errores cuando el JSON del request es inválido.

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<GenericResponse> handleInvalidJson(HttpMessageNotReadableException ex) {
        logger.warn("JSON inválido en la petición: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(GenericResponse.build(List.of("El formato del cuerpo de la petición es inválido.")));
    }


    //Maneja cualquier otra excepción no controlada.

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericResponse> handleGenericException(Exception ex) {
        logger.error("Error inesperado: {}", ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(GenericResponse.build(List.of("Ha ocurrido un error interno. Por favor, intente más tarde.")));
    }
}

