package com.agrosync.application.usecase.cobros.impl;

import com.agrosync.application.primaryports.dto.cobros.request.CobroIdSuscripcionDTO;
import com.agrosync.application.secondaryports.entity.cobros.CobroEntity;
import com.agrosync.application.secondaryports.mapper.cobros.CobroEntityMapper;
import com.agrosync.application.secondaryports.repository.CobroRepository;
import com.agrosync.application.usecase.cobros.ObtenerCobroPorId;
import com.agrosync.application.usecase.cobros.rulesvalidator.ObtenerCobroPorIdRulesValidator;
import com.agrosync.domain.cobros.CobroDomain;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ObtenerCobroPorIdImpl implements ObtenerCobroPorId {

    private final CobroRepository cobroRepository;
    private final ObtenerCobroPorIdRulesValidator obtenerCobroPorIdRulesValidator;
    private final CobroEntityMapper cobroEntityMapper;

    public ObtenerCobroPorIdImpl(CobroRepository cobroRepository,
                                  ObtenerCobroPorIdRulesValidator obtenerCobroPorIdRulesValidator,
                                  CobroEntityMapper cobroEntityMapper) {
        this.cobroRepository = cobroRepository;
        this.obtenerCobroPorIdRulesValidator = obtenerCobroPorIdRulesValidator;
        this.cobroEntityMapper = cobroEntityMapper;
    }

    @Override
    public CobroDomain ejecutar(CobroIdSuscripcionDTO data) {
        obtenerCobroPorIdRulesValidator.validar(data);
        Optional<CobroEntity> resultado = cobroRepository.findByIdAndSuscripcion_Id(data.getId(), data.getSuscripcionId());
        return cobroEntityMapper.toDomain(resultado.get());
    }
}
