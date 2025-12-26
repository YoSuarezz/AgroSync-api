package com.agrosync.application.primaryports.interactor.suscripcion.impl;

import com.agrosync.application.primaryports.dto.suscripcion.request.RegistrarSuscripcionDTO;
import com.agrosync.application.primaryports.interactor.suscripcion.RegistrarSuscripcionInteractor;
import com.agrosync.application.primaryports.mapper.suscripcion.SuscripcionDTOMapper;
import com.agrosync.application.usecase.suscripcion.RegistrarSuscripcion;
import com.agrosync.domain.suscripcion.SuscripcionDomain;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RegistrarSuscripcionInteractorImpl implements RegistrarSuscripcionInteractor {

    private final RegistrarSuscripcion registrarSuscripcion;

    public RegistrarSuscripcionInteractorImpl(RegistrarSuscripcion registrarSuscripcion) {
        this.registrarSuscripcion = registrarSuscripcion;
    }

    @Override
    public void ejecutar(RegistrarSuscripcionDTO data) {
        SuscripcionDomain domain = SuscripcionDTOMapper.INSTANCE.toDomain(data);
        registrarSuscripcion.ejecutar(domain);
    }
}
