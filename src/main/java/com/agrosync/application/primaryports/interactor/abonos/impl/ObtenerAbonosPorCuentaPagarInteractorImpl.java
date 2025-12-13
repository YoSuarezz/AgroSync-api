package com.agrosync.application.primaryports.interactor.abonos.impl;

import com.agrosync.application.primaryports.dto.abonos.request.AbonoIdSuscripcionDTO;
import com.agrosync.application.primaryports.dto.abonos.response.ObtenerAbonoDTO;
import com.agrosync.application.primaryports.interactor.abonos.ObtenerAbonosPorCuentaPagarInteractor;
import com.agrosync.application.primaryports.mapper.abonos.AbonoDTOMapper;
import com.agrosync.application.usecase.abonos.ObtenerAbonosPorCuentaPagar;
import com.agrosync.domain.abonos.AbonoDomain;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ObtenerAbonosPorCuentaPagarInteractorImpl implements ObtenerAbonosPorCuentaPagarInteractor {

    private final ObtenerAbonosPorCuentaPagar obtenerAbonosPorCuentaPagar;

    public ObtenerAbonosPorCuentaPagarInteractorImpl(ObtenerAbonosPorCuentaPagar obtenerAbonosPorCuentaPagar) {
        this.obtenerAbonosPorCuentaPagar = obtenerAbonosPorCuentaPagar;
    }

    @Override
    public List<ObtenerAbonoDTO> ejecutar(AbonoIdSuscripcionDTO data) {
        List<AbonoDomain> domains = obtenerAbonosPorCuentaPagar.ejecutar(data);
        return AbonoDTOMapper.INSTANCE.toObtenerDTOCollection(domains);
    }
}
