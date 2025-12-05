package com.agrosync.infrastructure.primaryadapters.adapter.response.suscripcion;

import com.agrosync.application.primaryports.dto.suscripcion.response.ObtenerSuscripcionDTO;
import com.agrosync.infrastructure.primaryadapters.adapter.response.PageResponse;
import com.agrosync.infrastructure.primaryadapters.adapter.response.ResponseWithData;

import java.util.List;

public class ObtenerSuscripcionesResponse extends ResponseWithData<PageResponse<ObtenerSuscripcionDTO>> {

    public static ObtenerSuscripcionesResponse build(final List<String> mensajes, final PageResponse<ObtenerSuscripcionDTO> datos) {
        ObtenerSuscripcionesResponse response = new ObtenerSuscripcionesResponse();
        response.setMensajes(mensajes);
        response.setDatos(datos);
        return response;
    }
}
