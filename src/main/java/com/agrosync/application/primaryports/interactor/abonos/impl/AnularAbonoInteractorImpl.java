package com.agrosync.application.primaryports.interactor.abonos.impl;

import com.agrosync.application.primaryports.dto.abonos.request.AnularAbonoDTO;
import com.agrosync.application.primaryports.interactor.abonos.AnularAbonoInteractor;
import com.agrosync.application.usecase.abonos.AnularAbono;
import com.agrosync.domain.abonos.AbonoDomain;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AnularAbonoInteractorImpl implements AnularAbonoInteractor {

    private final AnularAbono anularAbono;

    public AnularAbonoInteractorImpl(AnularAbono anularAbono) {
        this.anularAbono = anularAbono;
    }

    @Override
    public void ejecutar(AnularAbonoDTO data) {
        AbonoDomain abono = new AbonoDomain();
        abono.setId(data.getAbonoId());
        abono.setSuscripcionId(data.getSuscripcionId());
        abono.setMotivoAnulacion(data.getMotivo());
        anularAbono.ejecutar(abono);
    }
}

