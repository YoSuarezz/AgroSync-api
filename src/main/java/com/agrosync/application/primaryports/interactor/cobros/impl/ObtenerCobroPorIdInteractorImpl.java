package com.agrosync.application.primaryports.interactor.cobros.impl;

import com.agrosync.application.primaryports.dto.cobros.response.ObtenerCobroDTO;
import com.agrosync.application.primaryports.interactor.cobros.ObtenerCobroPorIdInteractor;
import com.agrosync.application.usecase.cobros.ObtenerCobroPorId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class ObtenerCobroPorIdInteractorImpl implements ObtenerCobroPorIdInteractor {

    private final ObtenerCobroPorId obtenerCobroPorId;

    public ObtenerCobroPorIdInteractorImpl(ObtenerCobroPorId obtenerCobroPorId) {
        this.obtenerCobroPorId = obtenerCobroPorId;
    }

    @Override
    public ObtenerCobroDTO ejecutar(UUID[] data) {
        return obtenerCobroPorId.ejecutar(data);
    }
}
