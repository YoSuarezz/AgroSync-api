package com.agrosync.application.usecase.abonos.impl;

import com.agrosync.application.primaryports.dto.abonos.request.AbonoIdSuscripcionDTO;
import com.agrosync.application.secondaryports.entity.abonos.AbonoEntity;
import com.agrosync.application.secondaryports.mapper.abonos.AbonoEntityMapper;
import com.agrosync.application.secondaryports.repository.AbonoRepository;
import com.agrosync.application.usecase.abonos.ObtenerAbonoPorId;
import com.agrosync.application.usecase.abonos.rulesvalidator.ObtenerAbonoPorIdRulesValidator;
import com.agrosync.domain.abonos.AbonoDomain;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ObtenerAbonoPorIdImpl implements ObtenerAbonoPorId {

    private final AbonoRepository abonoRepository;
    private final ObtenerAbonoPorIdRulesValidator obtenerAbonoPorIdRulesValidator;
    private final AbonoEntityMapper abonoEntityMapper;

    public ObtenerAbonoPorIdImpl(AbonoRepository abonoRepository,
                                  ObtenerAbonoPorIdRulesValidator obtenerAbonoPorIdRulesValidator,
                                  AbonoEntityMapper abonoEntityMapper) {
        this.abonoRepository = abonoRepository;
        this.obtenerAbonoPorIdRulesValidator = obtenerAbonoPorIdRulesValidator;
        this.abonoEntityMapper = abonoEntityMapper;
    }

    @Override
    public AbonoDomain ejecutar(AbonoIdSuscripcionDTO data) {
        obtenerAbonoPorIdRulesValidator.validar(data);
        Optional<AbonoEntity> resultado = abonoRepository.findByIdAndSuscripcion_Id(data.getId(), data.getSuscripcionId());
        return abonoEntityMapper.toDomain(resultado.get());
    }
}
