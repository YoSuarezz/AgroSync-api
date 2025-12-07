package com.agrosync.application.primaryports.interactor.cuentaspagar.impl;

import com.agrosync.application.primaryports.dto.cuentaspagar.request.CuentaPagarIdSuscripcionDTO;
import com.agrosync.application.primaryports.dto.cuentaspagar.response.ObtenerCuentaPagarDTO;
import com.agrosync.application.primaryports.interactor.cuentaspagar.ObtenerCuentaPagarPorIdInteractor;
import com.agrosync.application.primaryports.mapper.cuentaspagar.CuentaPagarDTOMapper;
import com.agrosync.application.usecase.cuentaspagar.ObtenerCuentaPagarPorId;
import com.agrosync.domain.cuentaspagar.CuentaPagarDomain;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ObtenerCuentaPagarPorIdInteractorImpl implements ObtenerCuentaPagarPorIdInteractor {

    private final ObtenerCuentaPagarPorId obtenerCuentaPagarPorId;

    public ObtenerCuentaPagarPorIdInteractorImpl(ObtenerCuentaPagarPorId obtenerCuentaPagarPorId) {
        this.obtenerCuentaPagarPorId = obtenerCuentaPagarPorId;
    }

    @Override
    public ObtenerCuentaPagarDTO ejecutar(CuentaPagarIdSuscripcionDTO data) {
        CuentaPagarDomain resultado = obtenerCuentaPagarPorId.ejecutar(data);
        return CuentaPagarDTOMapper.INSTANCE.toObtenerDTO(resultado);
    }
}
