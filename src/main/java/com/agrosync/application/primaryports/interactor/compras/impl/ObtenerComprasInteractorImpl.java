package com.agrosync.application.primaryports.interactor.compras.impl;

import com.agrosync.application.primaryports.dto.compras.request.CompraPageDTO;
import com.agrosync.application.primaryports.dto.compras.response.ObtenerCompraDTO;
import com.agrosync.application.primaryports.interactor.compras.ObtenerComprasInteractor;
import com.agrosync.application.primaryports.mapper.compras.CompraDTOMapper;
import com.agrosync.application.usecase.compras.ObtenerCompras;
import com.agrosync.domain.compras.CompraDomain;
import com.agrosync.infrastructure.primaryadapters.adapter.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ObtenerComprasInteractorImpl implements ObtenerComprasInteractor {

    private final ObtenerCompras obtenerCompras;

    public ObtenerComprasInteractorImpl(ObtenerCompras obtenerCompras) {
        this.obtenerCompras = obtenerCompras;
    }

    @Override
    public PageResponse<ObtenerCompraDTO> ejecutar(CompraPageDTO data) {
        Page<CompraDomain> resultados = obtenerCompras.ejecutar(data);
        Page<ObtenerCompraDTO> dtoPage = CompraDTOMapper.INSTANCE.toObtenerDTOCollection(resultados);
        return PageResponse.from(dtoPage);
    }
}
