package com.agrosync.infrastructure.primaryadapters.adapter.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public final class GenerateResponse {

    private GenerateResponse() {
    }

    public static ResponseEntity<GenericResponse> generateSuccessResponse(final List<String> mensajes) {
        GenericResponse genericResponse = GenericResponse.build(mensajes);
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }

    public static ResponseEntity<GenericResponse> generateBadRequestResponse(final List<String> mensajes) {
        GenericResponse genericResponse = GenericResponse.build(mensajes);
        return new ResponseEntity<>(genericResponse, HttpStatus.BAD_REQUEST);
    }

    public static <T> ResponseEntity<T> generateSuccessResponseWithData(final T responseWithData) {
        return new ResponseEntity<>(responseWithData, HttpStatus.OK);
    }

    public static <T> ResponseEntity<T> generateBadRequestResponseWithData(final T responseWithData) {
        return new ResponseEntity<>(responseWithData, HttpStatus.BAD_REQUEST);
    }
}
