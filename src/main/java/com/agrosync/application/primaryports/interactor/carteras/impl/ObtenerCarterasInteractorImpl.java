package com.agrosync.application.primaryports.interactor.carteras.impl;

import com.agrosync.application.primaryports.dto.carteras.request.CarteraPageDTO;
import com.agrosync.application.primaryports.dto.carteras.response.ObtenerCarteraDTO;
import com.agrosync.application.primaryports.interactor.carteras.ObtenerCarterasInteractor;
import com.agrosync.application.primaryports.mapper.carteras.CarteraDTOMapper;
import com.agrosync.application.usecase.carteras.ObtenerCarteras;
import com.agrosync.domain.carteras.CarteraDomain;
import com.agrosync.infrastructure.primaryadapters.adapter.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ObtenerCarterasInteractorImpl implements ObtenerCarterasInteractor {

    private final ObtenerCarteras obtenerCarteras;

    public ObtenerCarterasInteractorImpl(ObtenerCarteras obtenerCarteras) {
        this.obtenerCarteras = obtenerCarteras;
    }

    @Override
    public PageResponse<ObtenerCarteraDTO> ejecutar(CarteraPageDTO data) {
        Page<CarteraDomain> resultados = obtenerCarteras.ejecutar(data);
        Page<ObtenerCarteraDTO> dtoPage = CarteraDTOMapper.INSTANCE.toObtenerDTOCollection(resultados);
        return PageResponse.from(dtoPage);
    }
}
