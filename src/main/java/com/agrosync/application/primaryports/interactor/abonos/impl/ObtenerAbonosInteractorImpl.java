package com.agrosync.application.primaryports.interactor.abonos.impl;

import com.agrosync.application.primaryports.dto.abonos.request.AbonoPageDTO;
import com.agrosync.application.primaryports.dto.abonos.response.ObtenerAbonoDTO;
import com.agrosync.application.primaryports.interactor.abonos.ObtenerAbonosInteractor;
import com.agrosync.application.primaryports.mapper.abonos.AbonoDTOMapper;
import com.agrosync.application.usecase.abonos.ObtenerAbonos;
import com.agrosync.domain.abonos.AbonoDomain;
import com.agrosync.infrastructure.primaryadapters.adapter.response.PageResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ObtenerAbonosInteractorImpl implements ObtenerAbonosInteractor {

    private final ObtenerAbonos obtenerAbonos;

    public ObtenerAbonosInteractorImpl(ObtenerAbonos obtenerAbonos) {
        this.obtenerAbonos = obtenerAbonos;
    }

    @Override
    public PageResponse<ObtenerAbonoDTO> ejecutar(AbonoPageDTO data) {
        Page<AbonoDomain> resultados = obtenerAbonos.ejecutar(data);
        Page<ObtenerAbonoDTO> dtoPage = AbonoDTOMapper.INSTANCE.toObtenerDTOCollection(resultados);
        return PageResponse.from(dtoPage);
    }
}
