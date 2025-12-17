package com.agrosync.domain.animales.rules.impl;

import com.agrosync.application.secondaryports.repository.AnimalRepository;
import com.agrosync.domain.animales.AnimalDomain;
import com.agrosync.domain.animales.exceptions.NumeroAnimalDuplicadoException;
import com.agrosync.domain.animales.rules.NumeroAnimalUnicoRule;
import org.springframework.stereotype.Service;

@Service
public class NumeroAnimalUnicoRuleImpl implements NumeroAnimalUnicoRule {

    private final AnimalRepository animalRepository;

    public NumeroAnimalUnicoRuleImpl(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @Override
    public void validate(AnimalDomain data) {
        if (animalRepository.existsByNumeroAnimalAndSuscripcion_Id(
                data.getNumeroAnimal(),
                data.getSuscripcionId()
        )) {
            throw NumeroAnimalDuplicadoException.create();
        }
    }
}

