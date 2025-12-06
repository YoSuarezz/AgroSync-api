package com.agrosync.application.primaryports.interactor.lotes.impl;


import com.agrosync.application.primaryports.dto.lotes.request.LotePageDTO;
import com.agrosync.application.primaryports.dto.lotes.response.ObtenerLoteDTO;
import com.agrosync.application.primaryports.interactor.lotes.ObtenerLotesInteractor;
import com.agrosync.application.primaryports.mapper.lotes.LoteDTOMapper;
import com.agrosync.application.usecase.lotes.ObtenerLotes;
import com.agrosync.domain.lotes.LoteDomain;
import com.agrosync.infrastructure.primaryadapters.adapter.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ObtenerLotesInteractorImpl implements ObtenerLotesInteractor {

    private final ObtenerLotes obtenerLotes;

    public ObtenerLotesInteractorImpl(ObtenerLotes obtenerLotes) {
        this.obtenerLotes = obtenerLotes;
    }

    @Override
    public PageResponse<ObtenerLoteDTO> ejecutar(LotePageDTO data) {
        Page<LoteDomain> resultados = obtenerLotes.ejecutar(data);
        Page<ObtenerLoteDTO> dtoPage = LoteDTOMapper.INSTANCE.toObtenerDTOCollection(resultados);
        return PageResponse.from(dtoPage);
    }
}
