package com.agrosync.application.primaryports.interactor.cobros.impl;

import com.agrosync.application.primaryports.dto.cobros.request.AnularCobroDTO;
import com.agrosync.application.primaryports.interactor.cobros.AnularCobroInteractor;
import com.agrosync.application.usecase.cobros.AnularCobro;
import com.agrosync.domain.cobros.CobroDomain;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AnularCobroInteractorImpl implements AnularCobroInteractor {

    private final AnularCobro anularCobro;

    public AnularCobroInteractorImpl(AnularCobro anularCobro) {
        this.anularCobro = anularCobro;
    }

    @Override
    public void ejecutar(AnularCobroDTO data) {
        CobroDomain cobro = new CobroDomain();
        cobro.setId(data.getCobroId());
        cobro.setSuscripcionId(data.getSuscripcionId());
        cobro.setMotivoAnulacion(data.getMotivo());
        anularCobro.ejecutar(cobro);
    }
}

