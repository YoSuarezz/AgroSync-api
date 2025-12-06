package com.agrosync.application.usecase.animales.impl;


import com.agrosync.application.secondaryports.entity.animales.AnimalEntity;
import com.agrosync.application.secondaryports.mapper.animales.AnimalEntityMapper;
import com.agrosync.application.secondaryports.repository.AnimalRepository;
import com.agrosync.application.usecase.animales.RegistrarNuevoAnimal;
import com.agrosync.application.usecase.animales.rulesvalidator.RegistrarNuevoAnimalRulesValidator;
import com.agrosync.domain.animales.AnimalDomain;
import org.springframework.stereotype.Service;

@Service
public class RegistrarNuevoAnimalImpl implements RegistrarNuevoAnimal {

    private final AnimalRepository animalRepository;
    private final RegistrarNuevoAnimalRulesValidator registrarNuevoAnimalRulesValidator;
    private final AnimalEntityMapper animalEntityMapper;

    public RegistrarNuevoAnimalImpl(AnimalRepository animalRepository,
                                    RegistrarNuevoAnimalRulesValidator registrarNuevoAnimalRulesValidator,
                                    AnimalEntityMapper animalEntityMapper) {
        this.animalRepository = animalRepository;
        this.registrarNuevoAnimalRulesValidator = registrarNuevoAnimalRulesValidator;
        this.animalEntityMapper = animalEntityMapper;
    }

    @Override
    public void ejecutar(AnimalDomain data) {
        registrarNuevoAnimalRulesValidator.validar(data);
        AnimalEntity animalEntity = animalEntityMapper.toEntity(data);
        animalRepository.save(animalEntity);
    }
}
