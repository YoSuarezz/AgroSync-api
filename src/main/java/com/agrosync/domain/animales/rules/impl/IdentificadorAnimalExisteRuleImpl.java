package com.agrosync.domain.animales.rules.impl;

import com.agrosync.application.primaryports.dto.animales.request.AnimalIdSuscripcionDTO;
import com.agrosync.application.secondaryports.repository.AnimalRepository;
import com.agrosync.domain.animales.exceptions.IdentificadorAnimalNoExisteException;
import com.agrosync.domain.animales.rules.IdentificadorAnimalExisteRule;
import org.springframework.stereotype.Service;

@Service
public class IdentificadorAnimalExisteRuleImpl implements IdentificadorAnimalExisteRule {

    private final AnimalRepository animalRepository;

    public IdentificadorAnimalExisteRuleImpl(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @Override
    public void validate(AnimalIdSuscripcionDTO data) {
        boolean exists = animalRepository.existsByIdAndSuscripcion_Id(data.getId(), data.getSuscripcionId());
        if (!exists) {
            throw IdentificadorAnimalNoExisteException.create();
        }
    }
}