package com.agrosync.application.usecase.carteras.impl;

import com.agrosync.application.primaryports.dto.carteras.request.CarteraIdSuscripcionDTO;
import com.agrosync.application.secondaryports.entity.carteras.CarteraEntity;
import com.agrosync.application.secondaryports.mapper.carteras.CarteraEntityMapper;
import com.agrosync.application.secondaryports.repository.CarteraRepository;
import com.agrosync.application.usecase.carteras.ObtenerCarteraPorId;
import com.agrosync.application.usecase.carteras.rulesvalidator.ObtenerCarteraPorIdRulesValidator;
import com.agrosync.domain.carteras.CarteraDomain;
import com.agrosync.domain.carteras.exceptions.CarteraIdNoExisteException;
import org.springframework.stereotype.Service;

@Service
public class ObtenerCarteraPorIdImpl implements ObtenerCarteraPorId {

    private final CarteraRepository carteraRepository;
    private final ObtenerCarteraPorIdRulesValidator obtenerCarteraPorIdRulesValidator;
    private final CarteraEntityMapper carteraEntityMapper;

    public ObtenerCarteraPorIdImpl(CarteraRepository carteraRepository, ObtenerCarteraPorIdRulesValidator obtenerCarteraPorIdRulesValidator,
                                   CarteraEntityMapper carteraEntityMapper) {
        this.carteraRepository = carteraRepository;
        this.obtenerCarteraPorIdRulesValidator = obtenerCarteraPorIdRulesValidator;
        this.carteraEntityMapper = carteraEntityMapper;
    }

    @Override
    public CarteraDomain ejecutar(CarteraIdSuscripcionDTO data) {
        obtenerCarteraPorIdRulesValidator.validar(data);
        CarteraEntity resultado = carteraRepository.findByIdAndSuscripcion_Id(data.getId(), data.getSuscripcionId())
                .orElseThrow(CarteraIdNoExisteException::create);
        return carteraEntityMapper.toDomain(resultado);
    }
}
