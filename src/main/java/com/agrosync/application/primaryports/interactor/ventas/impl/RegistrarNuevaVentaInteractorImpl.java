package com.agrosync.application.primaryports.interactor.ventas.impl;

import com.agrosync.application.primaryports.dto.ventas.request.RegistrarNuevaVentaDTO;
import com.agrosync.application.primaryports.interactor.ventas.RegistrarNuevaVentaInteractor;
import com.agrosync.application.primaryports.mapper.ventas.VentaDTOMapper;
import com.agrosync.application.usecase.ventas.RegistrarNuevaVenta;
import com.agrosync.domain.ventas.VentaDomain;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RegistrarNuevaVentaInteractorImpl implements RegistrarNuevaVentaInteractor {

    private final RegistrarNuevaVenta registrarNuevaVenta;

    public RegistrarNuevaVentaInteractorImpl(RegistrarNuevaVenta registrarNuevaVenta) {
        this.registrarNuevaVenta = registrarNuevaVenta;
    }

    @Override
    public void ejecutar(RegistrarNuevaVentaDTO data) {
        VentaDomain venta = VentaDTOMapper.INSTANCE.toDomain(data);
        registrarNuevaVenta.ejecutar(venta);
    }
}
