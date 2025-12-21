package com.agrosync.application.primaryports.interactor.suscripcion.impl;

import com.agrosync.application.primaryports.dto.suscripcion.response.ObtenerSuscripcionPorIdDTO;
import com.agrosync.application.primaryports.interactor.suscripcion.ObtenerSuscripcionPorIdInteractor;
import com.agrosync.application.primaryports.mapper.suscripcion.SuscripcionDTOMapper;
import com.agrosync.application.usecase.suscripcion.ObtenerSuscripcionPorId;
import com.agrosync.domain.suscripcion.SuscripcionDomain;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
public class ObtenerSuscripcionPorIdInteractorImpl implements ObtenerSuscripcionPorIdInteractor {

    private final ObtenerSuscripcionPorId obtenerSuscripcionPorId;

    public ObtenerSuscripcionPorIdInteractorImpl(ObtenerSuscripcionPorId obtenerSuscripcionPorId) {
        this.obtenerSuscripcionPorId = obtenerSuscripcionPorId;
    }

    @Override
    public ObtenerSuscripcionPorIdDTO ejecutar(UUID data) {
        SuscripcionDomain domain = obtenerSuscripcionPorId.ejecutar(data);
        return SuscripcionDTOMapper.INSTANCE.toObtenerSuscripcionPorIdDTO(domain);
    }
}
