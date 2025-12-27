package com.agrosync.domain.animales.rules.impl;

import com.agrosync.application.secondaryports.repository.AnimalRepository;
import com.agrosync.domain.animales.AnimalDomain;
import com.agrosync.domain.animales.exceptions.AnimalNoDisponibleException;
import com.agrosync.domain.animales.exceptions.IdentificadorAnimalNoExisteException;
import com.agrosync.domain.animales.rules.AnimalDisponibleRule;
import com.agrosync.domain.enums.animales.EstadoAnimalEnum;
import org.springframework.stereotype.Service;

@Service
public class AnimalDisponibleRuleImpl implements AnimalDisponibleRule {

    private final AnimalRepository animalRepository;

    public AnimalDisponibleRuleImpl(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @Override
    public void validate(AnimalDomain data) {
        var animal = animalRepository.findByIdAndSuscripcion_Id(data.getId(), data.getSuscripcionId())
                .orElseThrow(IdentificadorAnimalNoExisteException::create);

        if (animal.getEstado() != EstadoAnimalEnum.DISPONIBLE) {
            throw AnimalNoDisponibleException.create();
        }
    }
}
