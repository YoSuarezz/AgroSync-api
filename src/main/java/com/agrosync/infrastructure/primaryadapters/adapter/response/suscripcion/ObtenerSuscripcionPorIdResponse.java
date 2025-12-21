package com.agrosync.infrastructure.primaryadapters.adapter.response.suscripcion;

import com.agrosync.application.primaryports.dto.suscripcion.response.ObtenerSuscripcionPorIdDTO;
import com.agrosync.infrastructure.primaryadapters.adapter.response.ResponseWithData;

import java.util.List;

public class ObtenerSuscripcionPorIdResponse extends ResponseWithData<ObtenerSuscripcionPorIdDTO> {

    public static ObtenerSuscripcionPorIdResponse build(final List<String> mensajes, final ObtenerSuscripcionPorIdDTO datos) {
        ObtenerSuscripcionPorIdResponse response = new ObtenerSuscripcionPorIdResponse();
        response.setMensajes(mensajes);
        response.setDatos(datos);
        return response;
    }
}
