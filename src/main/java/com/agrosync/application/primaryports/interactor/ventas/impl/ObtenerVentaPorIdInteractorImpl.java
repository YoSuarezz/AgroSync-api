package com.agrosync.application.primaryports.interactor.ventas.impl;

import com.agrosync.application.primaryports.dto.ventas.request.VentaIdSuscripcionDTO;
import com.agrosync.application.primaryports.dto.ventas.response.ObtenerVentaDetalleDTO;
import com.agrosync.application.primaryports.interactor.ventas.ObtenerVentaPorIdInteractor;
import com.agrosync.application.primaryports.mapper.ventas.VentaDTOMapper;
import com.agrosync.application.usecase.ventas.ObtenerVentaPorId;
import com.agrosync.domain.ventas.VentaDomain;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ObtenerVentaPorIdInteractorImpl implements ObtenerVentaPorIdInteractor {

    private final ObtenerVentaPorId obtenerVentaPorId;

    public ObtenerVentaPorIdInteractorImpl(ObtenerVentaPorId obtenerVentaPorId) {
        this.obtenerVentaPorId = obtenerVentaPorId;
    }

    @Override
    public ObtenerVentaDetalleDTO ejecutar(VentaIdSuscripcionDTO data) {
        VentaDomain resultado = obtenerVentaPorId.ejecutar(data);
        return VentaDTOMapper.INSTANCE.toObtenerDetalleDTO(resultado);
    }
}
