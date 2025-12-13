package com.agrosync.application.primaryports.interactor.cobros.impl;

import com.agrosync.application.primaryports.dto.cobros.request.CobroPageDTO;
import com.agrosync.application.primaryports.dto.cobros.response.ObtenerCobroDTO;
import com.agrosync.application.primaryports.interactor.cobros.ObtenerCobrosInteractor;
import com.agrosync.application.primaryports.mapper.cobros.CobroDTOMapper;
import com.agrosync.application.usecase.cobros.ObtenerCobros;
import com.agrosync.domain.cobros.CobroDomain;
import com.agrosync.infrastructure.primaryadapters.adapter.response.PageResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ObtenerCobrosInteractorImpl implements ObtenerCobrosInteractor {

    private final ObtenerCobros obtenerCobros;

    public ObtenerCobrosInteractorImpl(ObtenerCobros obtenerCobros) {
        this.obtenerCobros = obtenerCobros;
    }

    @Override
    public PageResponse<ObtenerCobroDTO> ejecutar(CobroPageDTO data) {
        Page<CobroDomain> resultados = obtenerCobros.ejecutar(data);
        Page<ObtenerCobroDTO> dtoPage = CobroDTOMapper.INSTANCE.toObtenerDTOCollection(resultados);
        return PageResponse.from(dtoPage);
    }
}

