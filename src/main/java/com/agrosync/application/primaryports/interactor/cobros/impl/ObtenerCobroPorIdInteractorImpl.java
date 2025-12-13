package com.agrosync.application.primaryports.interactor.cobros.impl;

import com.agrosync.application.primaryports.dto.cobros.request.CobroIdSuscripcionDTO;
import com.agrosync.application.primaryports.dto.cobros.response.ObtenerCobroDTO;
import com.agrosync.application.primaryports.interactor.cobros.ObtenerCobroPorIdInteractor;
import com.agrosync.application.primaryports.mapper.cobros.CobroDTOMapper;
import com.agrosync.application.usecase.cobros.ObtenerCobroPorId;
import com.agrosync.domain.cobros.CobroDomain;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ObtenerCobroPorIdInteractorImpl implements ObtenerCobroPorIdInteractor {

    private final ObtenerCobroPorId obtenerCobroPorId;

    public ObtenerCobroPorIdInteractorImpl(ObtenerCobroPorId obtenerCobroPorId) {
        this.obtenerCobroPorId = obtenerCobroPorId;
    }

    @Override
    public ObtenerCobroDTO ejecutar(CobroIdSuscripcionDTO data) {
        CobroDomain domain = obtenerCobroPorId.ejecutar(data);
        return CobroDTOMapper.INSTANCE.toObtenerDTO(domain);
    }
}
