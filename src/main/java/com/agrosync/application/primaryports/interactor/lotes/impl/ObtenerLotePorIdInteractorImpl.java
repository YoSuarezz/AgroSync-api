package com.agrosync.application.primaryports.interactor.lotes.impl;

import com.agrosync.application.primaryports.dto.lotes.request.LoteIdSuscripcionDTO;
import com.agrosync.application.primaryports.dto.lotes.response.ObtenerLoteDTO;
import com.agrosync.application.primaryports.interactor.lotes.ObtenerLotePorIdInteractor;
import com.agrosync.application.primaryports.mapper.lotes.LoteDTOMapper;
import com.agrosync.application.usecase.lotes.ObtenerLotePorId;
import com.agrosync.domain.lotes.LoteDomain;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ObtenerLotePorIdInteractorImpl implements ObtenerLotePorIdInteractor {

    private final ObtenerLotePorId obtenerLotePorId;

    public ObtenerLotePorIdInteractorImpl(ObtenerLotePorId obtenerLotePorId) {
        this.obtenerLotePorId = obtenerLotePorId;
    }

    @Override
    public ObtenerLoteDTO ejecutar(LoteIdSuscripcionDTO data) {
        LoteDomain resultado = obtenerLotePorId.ejecutar(data);
        return LoteDTOMapper.INSTANCE.toObtenerDTO(resultado);
    }
}
