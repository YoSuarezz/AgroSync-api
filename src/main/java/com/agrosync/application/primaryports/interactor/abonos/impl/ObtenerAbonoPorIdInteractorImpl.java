package com.agrosync.application.primaryports.interactor.abonos.impl;

import com.agrosync.application.primaryports.dto.abonos.request.AbonoIdSuscripcionDTO;
import com.agrosync.application.primaryports.dto.abonos.response.ObtenerAbonoDTO;
import com.agrosync.application.primaryports.interactor.abonos.ObtenerAbonoPorIdInteractor;
import com.agrosync.application.primaryports.mapper.abonos.AbonoDTOMapper;
import com.agrosync.application.usecase.abonos.ObtenerAbonoPorId;
import com.agrosync.domain.abonos.AbonoDomain;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ObtenerAbonoPorIdInteractorImpl implements ObtenerAbonoPorIdInteractor {

    private final ObtenerAbonoPorId obtenerAbonoPorId;

    public ObtenerAbonoPorIdInteractorImpl(ObtenerAbonoPorId obtenerAbonoPorId) {
        this.obtenerAbonoPorId = obtenerAbonoPorId;
    }

    @Override
    public ObtenerAbonoDTO ejecutar(AbonoIdSuscripcionDTO data) {
        AbonoDomain domain = obtenerAbonoPorId.ejecutar(data);
        return AbonoDTOMapper.INSTANCE.toObtenerDTO(domain);
    }
}
