package com.agrosync.application.primaryports.interactor.suscripcion.impl;

import com.agrosync.application.primaryports.dto.suscripcion.request.ActualizarEstadoSuscripcionDTO;
import com.agrosync.application.primaryports.interactor.suscripcion.ActualizarEstadoSuscripcionInteractor;
import com.agrosync.application.primaryports.mapper.suscripcion.SuscripcionDTOMapper;
import com.agrosync.application.usecase.suscripcion.ActualizarEstadoSuscripcion;
import com.agrosync.domain.suscripcion.SuscripcionDomain;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ActualizarEstadoSuscripcionInteractorImpl implements ActualizarEstadoSuscripcionInteractor {

    private final ActualizarEstadoSuscripcion actualizarEstadoSuscripcion;

    public ActualizarEstadoSuscripcionInteractorImpl(ActualizarEstadoSuscripcion actualizarEstadoSuscripcion) {
        this.actualizarEstadoSuscripcion = actualizarEstadoSuscripcion;
    }

    @Override
    public void ejecutar(ActualizarEstadoSuscripcionDTO data) {
        SuscripcionDomain domain = SuscripcionDTOMapper.INSTANCE.toDomain(data);
        actualizarEstadoSuscripcion.ejecutar(domain);
    }
}
