package com.agrosync.application.usecase.cobros.impl;

import com.agrosync.application.primaryports.dto.cobros.request.CobroIdSuscripcionDTO;
import com.agrosync.application.secondaryports.entity.cobros.CobroEntity;
import com.agrosync.application.secondaryports.mapper.cobros.CobroEntityMapper;
import com.agrosync.application.secondaryports.repository.CobroRepository;
import com.agrosync.application.usecase.cobros.ObtenerCobrosPorCuentaCobrar;
import com.agrosync.domain.cobros.CobroDomain;
import com.agrosync.domain.cuentascobrar.rules.IdentificadorCuentaCobrarExisteRule;
import com.agrosync.domain.suscripcion.rules.SuscripcionExisteRule;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObtenerCobrosPorCuentaCobrarImpl implements ObtenerCobrosPorCuentaCobrar {

    private final CobroRepository cobroRepository;
    private final CobroEntityMapper cobroEntityMapper;
    private final SuscripcionExisteRule suscripcionExisteRule;
    private final IdentificadorCuentaCobrarExisteRule identificadorCuentaCobrarExisteRule;

    public ObtenerCobrosPorCuentaCobrarImpl(CobroRepository cobroRepository,
                                            CobroEntityMapper cobroEntityMapper,
                                            SuscripcionExisteRule suscripcionExisteRule,
                                            IdentificadorCuentaCobrarExisteRule identificadorCuentaCobrarExisteRule) {
        this.cobroRepository = cobroRepository;
        this.cobroEntityMapper = cobroEntityMapper;
        this.suscripcionExisteRule = suscripcionExisteRule;
        this.identificadorCuentaCobrarExisteRule = identificadorCuentaCobrarExisteRule;
    }

    @Override
    public List<CobroDomain> ejecutar(CobroIdSuscripcionDTO data) {
        suscripcionExisteRule.validate(data.getSuscripcionId());
        identificadorCuentaCobrarExisteRule.validate(data.getId());

        List<CobroEntity> cobros = cobroRepository.findByCuentaCobrar_IdAndSuscripcion_Id(
                data.getId(), data.getSuscripcionId());

        return cobroEntityMapper.toDomainCollection(cobros);
    }
}
