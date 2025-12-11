package com.agrosync.application.primaryports.interactor.abonos.impl;

import com.agrosync.application.primaryports.dto.abonos.request.RegistrarAbonoDTO;
import com.agrosync.application.primaryports.interactor.abonos.RegistrarNuevoAbonoInteractor;
import com.agrosync.application.usecase.abonos.RegistrarNuevoAbono;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RegistrarNuevoAbonoInteractorImpl implements RegistrarNuevoAbonoInteractor {

    private final RegistrarNuevoAbono registrarNuevoAbono;

    public RegistrarNuevoAbonoInteractorImpl(RegistrarNuevoAbono registrarNuevoAbono) {
        this.registrarNuevoAbono = registrarNuevoAbono;
    }

    @Override
    public void ejecutar(RegistrarAbonoDTO data) {
        registrarNuevoAbono.ejecutar(data);
    }
}
