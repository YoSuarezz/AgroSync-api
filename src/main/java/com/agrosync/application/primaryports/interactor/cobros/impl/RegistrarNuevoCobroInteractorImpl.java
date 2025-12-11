package com.agrosync.application.primaryports.interactor.cobros.impl;

import com.agrosync.application.primaryports.dto.cobros.request.RegistrarCobroDTO;
import com.agrosync.application.primaryports.interactor.cobros.RegistrarNuevoCobroInteractor;
import com.agrosync.application.usecase.cobros.RegistrarNuevoCobro;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RegistrarNuevoCobroInteractorImpl implements RegistrarNuevoCobroInteractor {

    private final RegistrarNuevoCobro registrarNuevoCobro;

    public RegistrarNuevoCobroInteractorImpl(RegistrarNuevoCobro registrarNuevoCobro) {
        this.registrarNuevoCobro = registrarNuevoCobro;
    }

    @Override
    public void ejecutar(RegistrarCobroDTO data) {
        registrarNuevoCobro.ejecutar(data);
    }
}
