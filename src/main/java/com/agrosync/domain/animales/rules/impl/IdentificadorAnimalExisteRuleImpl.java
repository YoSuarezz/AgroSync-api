package com.agrosync.domain.animales.rules.impl;

import com.agrosync.application.secondaryports.repository.AnimalRepository;
import com.agrosync.domain.animales.exceptions.IdentificadorAnimalNoExisteException;
import com.agrosync.domain.animales.rules.IdentificadorAnimalExisteRule;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class IdentificadorAnimalExisteRuleImpl implements IdentificadorAnimalExisteRule {

    private final AnimalRepository animalRepository;

    public IdentificadorAnimalExisteRuleImpl(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @Override
    public void validate(UUID id) {
        // Lógica de negocio: Verifica si el ID existe en la base de datos
        boolean exists = animalRepository.existsById(id);
        if (!exists) {
            throw IdentificadorAnimalNoExisteException.create();
        }
    }
}