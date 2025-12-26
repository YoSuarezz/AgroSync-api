package com.agrosync.application.usecase.suscripcion.impl;

import com.agrosync.application.secondaryports.entity.suscripcion.SuscripcionEntity;
import com.agrosync.application.secondaryports.mapper.suscripcion.SuscripcionEntityMapper;
import com.agrosync.application.secondaryports.repository.SuscripcionRepository;
import com.agrosync.application.usecase.suscripcion.ObtenerSuscripcionPorId;
import com.agrosync.application.usecase.suscripcion.rulesvalidator.ObtenerSuscripcionPorIdRulesValidator;
import com.agrosync.domain.suscripcion.SuscripcionDomain;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ObtenerSuscripcionPorIdImpl implements ObtenerSuscripcionPorId {

    private final SuscripcionRepository suscripcionRepository;
    private final ObtenerSuscripcionPorIdRulesValidator obtenerSuscripcionPorIdRulesValidator;
    private final SuscripcionEntityMapper suscripcionEntityMapper;

    public ObtenerSuscripcionPorIdImpl(SuscripcionRepository suscripcionRepository,
                                       ObtenerSuscripcionPorIdRulesValidator obtenerSuscripcionPorIdRulesValidator,
                                       SuscripcionEntityMapper suscripcionEntityMapper) {
        this.suscripcionRepository = suscripcionRepository;
        this.obtenerSuscripcionPorIdRulesValidator = obtenerSuscripcionPorIdRulesValidator;
        this.suscripcionEntityMapper = suscripcionEntityMapper;
    }

    @Override
    public SuscripcionDomain ejecutar(UUID data) {
        obtenerSuscripcionPorIdRulesValidator.validar(data);
        Optional<SuscripcionEntity> entity = suscripcionRepository.findById(data);
        return suscripcionEntityMapper.toDomain(entity.get());
    }
}
