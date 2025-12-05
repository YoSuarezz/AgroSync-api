package com.agrosync.application.primaryports.interactor.compras.impl;

import com.agrosync.application.primaryports.dto.compras.request.RegistrarNuevaCompraDTO;
import com.agrosync.application.primaryports.interactor.compras.RegistrarNuevaCompraInteractor;
import com.agrosync.application.primaryports.mapper.compras.CompraDTOMapper;
import com.agrosync.application.usecase.compras.RegistrarNuevaCompra;
import com.agrosync.domain.compras.CompraDomain;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RegistrarNuevaCompraInteractorImpl implements RegistrarNuevaCompraInteractor {

    private final RegistrarNuevaCompra registrarNuevaCompra;

    public RegistrarNuevaCompraInteractorImpl(RegistrarNuevaCompra registrarNuevaCompra) {
        this.registrarNuevaCompra = registrarNuevaCompra;
    }

    @Override
    public void ejecutar(RegistrarNuevaCompraDTO data) {
        CompraDomain compra = CompraDTOMapper.INSTANCE.toDomain(data);
        registrarNuevaCompra.ejecutar(compra);
    }
}
