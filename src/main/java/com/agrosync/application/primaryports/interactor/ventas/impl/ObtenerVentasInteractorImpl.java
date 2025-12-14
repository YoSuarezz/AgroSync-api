package com.agrosync.application.primaryports.interactor.ventas.impl;

import com.agrosync.application.primaryports.dto.ventas.request.VentaPageDTO;
import com.agrosync.application.primaryports.dto.ventas.response.ObtenerVentasDTO;
import com.agrosync.application.primaryports.interactor.ventas.ObtenerVentasInteractor;
import com.agrosync.application.primaryports.mapper.ventas.VentaDTOMapper;
import com.agrosync.application.usecase.ventas.ObtenerVentas;
import com.agrosync.domain.ventas.VentaDomain;
import com.agrosync.infrastructure.primaryadapters.adapter.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ObtenerVentasInteractorImpl implements ObtenerVentasInteractor {

    private final ObtenerVentas obtenerVentas;

    public ObtenerVentasInteractorImpl(ObtenerVentas obtenerVentas) {
        this.obtenerVentas = obtenerVentas;
    }

    @Override
    public PageResponse<ObtenerVentasDTO> ejecutar(VentaPageDTO data) {
        Page<VentaDomain> resultados = obtenerVentas.ejecutar(data);
        Page<ObtenerVentasDTO> dtoPage = VentaDTOMapper.INSTANCE.toObtenerDTOCollection(resultados);
        return PageResponse.from(dtoPage);
    }
}
