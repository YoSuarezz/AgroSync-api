package com.agrosync.application.primaryports.interactor.cobros.impl;

import com.agrosync.application.primaryports.dto.cobros.request.CobroIdSuscripcionDTO;
import com.agrosync.application.primaryports.dto.cobros.response.ObtenerCobroDTO;
import com.agrosync.application.primaryports.interactor.cobros.ObtenerCobrosPorCuentaCobrarInteractor;
import com.agrosync.application.primaryports.mapper.cobros.CobroDTOMapper;
import com.agrosync.application.usecase.cobros.ObtenerCobrosPorCuentaCobrar;
import com.agrosync.domain.cobros.CobroDomain;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ObtenerCobrosPorCuentaCobrarInteractorImpl implements ObtenerCobrosPorCuentaCobrarInteractor {

    private final ObtenerCobrosPorCuentaCobrar obtenerCobrosPorCuentaCobrar;

    public ObtenerCobrosPorCuentaCobrarInteractorImpl(ObtenerCobrosPorCuentaCobrar obtenerCobrosPorCuentaCobrar) {
        this.obtenerCobrosPorCuentaCobrar = obtenerCobrosPorCuentaCobrar;
    }

    @Override
    public List<ObtenerCobroDTO> ejecutar(CobroIdSuscripcionDTO data) {
        List<CobroDomain> domains = obtenerCobrosPorCuentaCobrar.ejecutar(data);
        return CobroDTOMapper.INSTANCE.toObtenerDTOCollection(domains);
    }
}
