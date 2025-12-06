package com.agrosync.application.usecase.animales.impl;

import com.agrosync.application.primaryports.dto.animales.request.AnimalIdSuscripcionDTO;
import com.agrosync.application.secondaryports.entity.animales.AnimalEntity;
import com.agrosync.application.secondaryports.mapper.animales.AnimalEntityMapper;
import com.agrosync.application.secondaryports.repository.AnimalRepository;
import com.agrosync.application.usecase.animales.ObtenerAnimalPorId;
import com.agrosync.application.usecase.animales.rulesvalidator.ObtenerAnimalPorIdRulesValidator;
import com.agrosync.domain.animales.AnimalDomain;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ObtenerAnimalPorIdImpl implements ObtenerAnimalPorId {

    private final AnimalRepository animalRepository;
    private final ObtenerAnimalPorIdRulesValidator obtenerAnimalPorIdRulesValidator;
    private final AnimalEntityMapper animalEntityMapper;

    public ObtenerAnimalPorIdImpl(AnimalRepository animalRepository, ObtenerAnimalPorIdRulesValidator obtenerAnimalPorIdRulesValidator, AnimalEntityMapper animalEntityMapper) {
        this.animalRepository = animalRepository;
        this.obtenerAnimalPorIdRulesValidator = obtenerAnimalPorIdRulesValidator;
        this.animalEntityMapper = animalEntityMapper;
    }

    @Override
    public AnimalDomain ejecutar(AnimalIdSuscripcionDTO data) {
        obtenerAnimalPorIdRulesValidator.validar(data);
        Optional<AnimalEntity> resultado = animalRepository.findByIdAndSuscripcion_Id(data.getId(), data.getSuscripcionId());
        return animalEntityMapper.toDomain(resultado.get());
    }
}