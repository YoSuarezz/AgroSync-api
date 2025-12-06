package com.agrosync.application.usecase.lotes.impl;

import com.agrosync.application.primaryports.dto.lotes.request.LoteIdSuscripcionDTO;
import com.agrosync.application.secondaryports.entity.lotes.LoteEntity;
import com.agrosync.application.secondaryports.mapper.lotes.LoteEntityMapper;
import com.agrosync.application.secondaryports.repository.LoteRepository;
import com.agrosync.application.usecase.lotes.ObtenerLotePorId;
import com.agrosync.application.usecase.lotes.rulesvalidator.ObtenerLotePorIdRulesValidator;
import com.agrosync.domain.lotes.LoteDomain;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ObtenerLotePorIdImpl implements ObtenerLotePorId {

    private final LoteRepository loteRepository;
    private final ObtenerLotePorIdRulesValidator obtenerLotePorIdRulesValidator;
    private final LoteEntityMapper loteEntityMapper;

    public ObtenerLotePorIdImpl(LoteRepository loteRepository,
                                ObtenerLotePorIdRulesValidator obtenerLotePorIdRulesValidator,
                                LoteEntityMapper loteEntityMapper) {
        this.loteRepository = loteRepository;
        this.obtenerLotePorIdRulesValidator = obtenerLotePorIdRulesValidator;
        this.loteEntityMapper = loteEntityMapper;
    }

    @Override
    public LoteDomain ejecutar(LoteIdSuscripcionDTO data) {
        obtenerLotePorIdRulesValidator.validar(data);
        Optional<LoteEntity> resultado = loteRepository.findByIdAndSuscripcion_Id(data.getId(), data.getSuscripcionId());
        return loteEntityMapper.toDomain(resultado.get());
    }
}