package com.agrosync.application.primaryports.interactor.ventas.impl;

import com.agrosync.application.primaryports.dto.ventas.request.EditarVentaDTO;
import com.agrosync.application.primaryports.interactor.ventas.EditarVentaInteractor;
import com.agrosync.application.usecase.ventas.EditarVenta;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EditarVentaInteractorImpl implements EditarVentaInteractor {

    private final EditarVenta editarVenta;

    public EditarVentaInteractorImpl(EditarVenta editarVenta) {
        this.editarVenta = editarVenta;
    }

    @Override
    public void ejecutar(EditarVentaDTO data) {
        editarVenta.ejecutar(data);
    }
}
