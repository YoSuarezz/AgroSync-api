package com.agrosync.application.primaryports.interactor.cuentascobrar.impl;

import com.agrosync.application.primaryports.dto.cuentascobrar.request.CuentaCobrarPageDTO;
import com.agrosync.application.primaryports.dto.cuentascobrar.response.ObtenerCuentaCobrarDTO;
import com.agrosync.application.primaryports.interactor.cuentascobrar.ObtenerCuentasCobrarInteractor;
import com.agrosync.application.primaryports.mapper.cuentascobrar.CuentaCobrarDTOMapper;
import com.agrosync.application.usecase.cuentascobrar.ObtenerCuentasCobrar;
import com.agrosync.domain.cuentascobrar.CuentaCobrarDomain;
import com.agrosync.infrastructure.primaryadapters.adapter.response.PageResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ObtenerCuentasCobrarInteractorImpl implements ObtenerCuentasCobrarInteractor {

    private final ObtenerCuentasCobrar obtenerCuentasCobrar;

    public ObtenerCuentasCobrarInteractorImpl(ObtenerCuentasCobrar obtenerCuentasCobrar) {
        this.obtenerCuentasCobrar = obtenerCuentasCobrar;
    }

    @Override
    public PageResponse<ObtenerCuentaCobrarDTO> ejecutar(CuentaCobrarPageDTO data) {
        Page<CuentaCobrarDomain> resultados = obtenerCuentasCobrar.ejecutar(data);
        Page<ObtenerCuentaCobrarDTO> dtoPage = CuentaCobrarDTOMapper.INSTANCE.toObtenerDTOCollection(resultados);
        return PageResponse.from(dtoPage);
    }
}
