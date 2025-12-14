package com.agrosync.application.primaryports.interactor.compras.impl;

import com.agrosync.application.primaryports.dto.compras.request.CompraIdSuscripcionDTO;
import com.agrosync.application.primaryports.dto.compras.response.ObtenerCompraDetalleDTO;
import com.agrosync.application.primaryports.interactor.compras.ObtenerCompraPorIdInteractor;
import com.agrosync.application.primaryports.mapper.compras.CompraDTOMapper;
import com.agrosync.application.usecase.compras.ObtenerCompraPorId;
import com.agrosync.domain.compras.CompraDomain;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ObtenerCompraPorIdInteractorImpl implements ObtenerCompraPorIdInteractor {

    private final ObtenerCompraPorId obtenerCompraPorId;

    public ObtenerCompraPorIdInteractorImpl(ObtenerCompraPorId obtenerCompraPorId) {
        this.obtenerCompraPorId = obtenerCompraPorId;
    }

    @Override
    public ObtenerCompraDetalleDTO ejecutar(CompraIdSuscripcionDTO data) {
        CompraDomain resultado = obtenerCompraPorId.ejecutar(data);
        return CompraDTOMapper.INSTANCE.toObtenerDetalleDTO(resultado);
    }
}
