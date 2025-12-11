package com.agrosync.application.primaryports.interactor.cobros.impl;

import com.agrosync.application.primaryports.dto.cobros.response.ObtenerCobroDTO;
import com.agrosync.application.primaryports.interactor.cobros.ObtenerCobrosPorCuentaCobrarInteractor;
import com.agrosync.application.usecase.cobros.ObtenerCobrosPorCuentaCobrar;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class ObtenerCobrosPorCuentaCobrarInteractorImpl implements ObtenerCobrosPorCuentaCobrarInteractor {

    private final ObtenerCobrosPorCuentaCobrar obtenerCobrosPorCuentaCobrar;

    public ObtenerCobrosPorCuentaCobrarInteractorImpl(ObtenerCobrosPorCuentaCobrar obtenerCobrosPorCuentaCobrar) {
        this.obtenerCobrosPorCuentaCobrar = obtenerCobrosPorCuentaCobrar;
    }

    @Override
    public List<ObtenerCobroDTO> ejecutar(UUID[] data) {
        return obtenerCobrosPorCuentaCobrar.ejecutar(data);
    }
}
