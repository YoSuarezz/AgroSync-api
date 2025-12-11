package com.agrosync.application.primaryports.interactor.abonos.impl;

import com.agrosync.application.primaryports.dto.abonos.response.ObtenerAbonoDTO;
import com.agrosync.application.primaryports.interactor.abonos.ObtenerAbonoPorIdInteractor;
import com.agrosync.application.usecase.abonos.ObtenerAbonoPorId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class ObtenerAbonoPorIdInteractorImpl implements ObtenerAbonoPorIdInteractor {

    private final ObtenerAbonoPorId obtenerAbonoPorId;

    public ObtenerAbonoPorIdInteractorImpl(ObtenerAbonoPorId obtenerAbonoPorId) {
        this.obtenerAbonoPorId = obtenerAbonoPorId;
    }

    @Override
    public ObtenerAbonoDTO ejecutar(UUID[] data) {
        return obtenerAbonoPorId.ejecutar(data);
    }
}
