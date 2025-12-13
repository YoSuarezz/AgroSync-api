package com.agrosync.application.primaryports.interactor.cobros.impl;

import com.agrosync.application.primaryports.dto.cobros.request.RegistrarCobroDTO;
import com.agrosync.application.primaryports.interactor.cobros.RegistrarNuevoCobroInteractor;
import com.agrosync.application.primaryports.mapper.cobros.CobroDTOMapper;
import com.agrosync.application.usecase.cobros.RegistrarNuevoCobro;
import com.agrosync.domain.cobros.CobroDomain;
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
        CobroDomain cobroDomain = CobroDTOMapper.INSTANCE.toDomain(data);
        registrarNuevoCobro.ejecutar(cobroDomain);
    }
}
