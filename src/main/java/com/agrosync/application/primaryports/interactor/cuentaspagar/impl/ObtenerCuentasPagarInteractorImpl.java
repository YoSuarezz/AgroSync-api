package com.agrosync.application.primaryports.interactor.cuentaspagar.impl;

import com.agrosync.application.primaryports.dto.cuentaspagar.request.CuentaPagarPageDTO;
import com.agrosync.application.primaryports.dto.cuentaspagar.response.ObtenerCuentaPagarDTO;
import com.agrosync.application.primaryports.interactor.cuentaspagar.ObtenerCuentasPagarInteractor;
import com.agrosync.application.primaryports.mapper.cuentaspagar.CuentaPagarDTOMapper;
import com.agrosync.application.usecase.cuentaspagar.ObtenerCuentasPagar;
import com.agrosync.domain.cuentaspagar.CuentaPagarDomain;
import com.agrosync.infrastructure.primaryadapters.adapter.response.PageResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ObtenerCuentasPagarInteractorImpl implements ObtenerCuentasPagarInteractor {

    private final ObtenerCuentasPagar obtenerCuentasPagar;

    public ObtenerCuentasPagarInteractorImpl(ObtenerCuentasPagar obtenerCuentasPagar) {
        this.obtenerCuentasPagar = obtenerCuentasPagar;
    }

    @Override
    public PageResponse<ObtenerCuentaPagarDTO> ejecutar(CuentaPagarPageDTO data) {
        Page<CuentaPagarDomain> resultados = obtenerCuentasPagar.ejecutar(data);
        Page<ObtenerCuentaPagarDTO> dtoPage = CuentaPagarDTOMapper.INSTANCE.toObtenerDTOCollection(resultados);
        return PageResponse.from(dtoPage);
    }
}
