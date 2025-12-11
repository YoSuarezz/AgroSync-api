package com.agrosync.application.primaryports.interactor.abonos.impl;

import com.agrosync.application.primaryports.dto.abonos.response.ObtenerAbonoDTO;
import com.agrosync.application.primaryports.interactor.abonos.ObtenerAbonosPorCuentaPagarInteractor;
import com.agrosync.application.usecase.abonos.ObtenerAbonosPorCuentaPagar;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class ObtenerAbonosPorCuentaPagarInteractorImpl implements ObtenerAbonosPorCuentaPagarInteractor {

    private final ObtenerAbonosPorCuentaPagar obtenerAbonosPorCuentaPagar;

    public ObtenerAbonosPorCuentaPagarInteractorImpl(ObtenerAbonosPorCuentaPagar obtenerAbonosPorCuentaPagar) {
        this.obtenerAbonosPorCuentaPagar = obtenerAbonosPorCuentaPagar;
    }

    @Override
    public List<ObtenerAbonoDTO> ejecutar(UUID[] data) {
        return obtenerAbonosPorCuentaPagar.ejecutar(data);
    }
}
