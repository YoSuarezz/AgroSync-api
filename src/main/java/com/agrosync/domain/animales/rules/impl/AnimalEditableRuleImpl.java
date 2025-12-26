package com.agrosync.domain.animales.rules.impl;

import com.agrosync.application.secondaryports.repository.AnimalRepository;
import com.agrosync.domain.animales.AnimalDomain;
import com.agrosync.domain.animales.exceptions.AnimalNoEditableException;
import com.agrosync.domain.animales.exceptions.IdentificadorAnimalNoExisteException;
import com.agrosync.domain.animales.rules.AnimalEditableRule;
import com.agrosync.domain.enums.animales.EstadoAnimalEnum;
import org.springframework.stereotype.Service;

@Service
public class AnimalEditableRuleImpl implements AnimalEditableRule {

    private final AnimalRepository animalRepository;

    public AnimalEditableRuleImpl(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @Override
    public void validate(AnimalDomain data) {
        var animalEntity = animalRepository.findByIdAndSuscripcion_Id(data.getId(), data.getSuscripcionId())
                .orElseThrow(IdentificadorAnimalNoExisteException::create);

        if (animalEntity.getEstado() == EstadoAnimalEnum.ANULADO) {
            throw AnimalNoEditableException.create();
        }
    }
}
