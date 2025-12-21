package com.agrosync.application.primaryports.interactor.suscripcion.impl;

import com.agrosync.application.primaryports.dto.suscripcion.request.SuscripcionPageDTO;
import com.agrosync.application.primaryports.dto.suscripcion.response.ObtenerSuscripcionDTO;
import com.agrosync.application.primaryports.interactor.suscripcion.ObtenerSuscripcionesInteractor;
import com.agrosync.application.primaryports.mapper.suscripcion.SuscripcionDTOMapper;
import com.agrosync.application.usecase.suscripcion.ObtenerSuscripciones;
import com.agrosync.domain.suscripcion.SuscripcionDomain;
import com.agrosync.infrastructure.primaryadapters.adapter.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ObtenerSuscripcionesInteractorImpl implements ObtenerSuscripcionesInteractor {

    private final ObtenerSuscripciones obtenerSuscripciones;

    public ObtenerSuscripcionesInteractorImpl(ObtenerSuscripciones obtenerSuscripciones) {
        this.obtenerSuscripciones = obtenerSuscripciones;
    }

    @Override
    public PageResponse<ObtenerSuscripcionDTO> ejecutar(SuscripcionPageDTO data) {
        Page<SuscripcionDomain> resultados = obtenerSuscripciones.ejecutar(data);
        Page<ObtenerSuscripcionDTO> paginaDto = SuscripcionDTOMapper.INSTANCE.toDTOCollection(resultados);
        return PageResponse.from(paginaDto);
    }
}
