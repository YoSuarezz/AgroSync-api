package com.agrosync.application.primaryports.interactor.suscripcion.impl;

import com.agrosync.application.primaryports.dto.suscripcion.request.ActualizarPlanSuscripcionDTO;
import com.agrosync.application.primaryports.interactor.suscripcion.ActualizarPlanSuscripcionInteractor;
import com.agrosync.application.primaryports.mapper.suscripcion.SuscripcionDTOMapper;
import com.agrosync.application.usecase.suscripcion.ActualizarPlanSuscripcion;
import com.agrosync.domain.suscripcion.SuscripcionDomain;
import org.springframework.stereotype.Service;

@Service
public class ActualizarPlanSuscripcionInteractorImpl implements ActualizarPlanSuscripcionInteractor {

    private final ActualizarPlanSuscripcion actualizarPlanSuscripcion;

    public ActualizarPlanSuscripcionInteractorImpl(ActualizarPlanSuscripcion actualizarPlanSuscripcion) {
        this.actualizarPlanSuscripcion = actualizarPlanSuscripcion;
    }

    @Override
    public void ejecutar(ActualizarPlanSuscripcionDTO data) {
        SuscripcionDomain domain = SuscripcionDTOMapper.INSTANCE.toDomain(data);
        actualizarPlanSuscripcion.ejecutar(domain);
    }
}
