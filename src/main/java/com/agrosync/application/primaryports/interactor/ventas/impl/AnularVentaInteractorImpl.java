package com.agrosync.application.primaryports.interactor.ventas.impl;

import com.agrosync.application.primaryports.dto.ventas.request.AnularVentaDTO;
import com.agrosync.application.primaryports.interactor.ventas.AnularVentaInteractor;
import com.agrosync.application.usecase.ventas.AnularVenta;
import com.agrosync.domain.ventas.VentaDomain;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AnularVentaInteractorImpl implements AnularVentaInteractor {

    private final AnularVenta anularVenta;

    public AnularVentaInteractorImpl(AnularVenta anularVenta) {
        this.anularVenta = anularVenta;
    }

    @Override
    public void ejecutar(AnularVentaDTO data) {
        VentaDomain venta = new VentaDomain();
        venta.setId(data.getVentaId());
        venta.setSuscripcionId(data.getSuscripcionId());
        venta.setMotivoAnulacion(data.getMotivo());
        anularVenta.ejecutar(venta);
    }
}

