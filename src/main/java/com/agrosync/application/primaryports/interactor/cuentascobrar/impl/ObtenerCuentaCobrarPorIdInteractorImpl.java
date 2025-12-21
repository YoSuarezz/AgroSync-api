package com.agrosync.application.primaryports.interactor.cuentascobrar.impl;

import com.agrosync.application.primaryports.dto.cuentascobrar.request.CuentaCobrarIdSuscripcionDTO;
import com.agrosync.application.primaryports.dto.cuentascobrar.response.ObtenerCuentaCobrarDTO;
import com.agrosync.application.primaryports.interactor.cuentascobrar.ObtenerCuentaCobrarPorIdInteractor;
import com.agrosync.application.primaryports.mapper.cuentascobrar.CuentaCobrarDTOMapper;
import com.agrosync.application.usecase.cuentascobrar.ObtenerCuentaCobrarPorId;
import com.agrosync.domain.cuentascobrar.CuentaCobrarDomain;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ObtenerCuentaCobrarPorIdInteractorImpl implements ObtenerCuentaCobrarPorIdInteractor {

    private final ObtenerCuentaCobrarPorId obtenerCuentaCobrarPorId;

    public ObtenerCuentaCobrarPorIdInteractorImpl(ObtenerCuentaCobrarPorId obtenerCuentaCobrarPorId) {
        this.obtenerCuentaCobrarPorId = obtenerCuentaCobrarPorId;
    }

    @Override
    public ObtenerCuentaCobrarDTO ejecutar(CuentaCobrarIdSuscripcionDTO data) {
        CuentaCobrarDomain resultado = obtenerCuentaCobrarPorId.ejecutar(data);
        return CuentaCobrarDTOMapper.INSTANCE.toObtenerDTO(resultado);
    }
}
