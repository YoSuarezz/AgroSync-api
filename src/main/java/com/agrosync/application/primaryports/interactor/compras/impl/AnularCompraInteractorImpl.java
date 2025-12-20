package com.agrosync.application.primaryports.interactor.compras.impl;

import com.agrosync.application.primaryports.dto.compras.request.AnularCompraDTO;
import com.agrosync.application.primaryports.interactor.compras.AnularCompraInteractor;
import com.agrosync.application.usecase.compras.AnularCompra;
import com.agrosync.domain.compras.CompraDomain;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AnularCompraInteractorImpl implements AnularCompraInteractor {

    private final AnularCompra anularCompra;

    public AnularCompraInteractorImpl(AnularCompra anularCompra) {
        this.anularCompra = anularCompra;
    }

    @Override
    public void ejecutar(AnularCompraDTO data) {
        CompraDomain compra = new CompraDomain();
        compra.setId(data.getCompraId());
        compra.setSuscripcionId(data.getSuscripcionId());
        compra.setMotivoAnulacion(data.getMotivo());
        anularCompra.ejecutar(compra);
    }
}

