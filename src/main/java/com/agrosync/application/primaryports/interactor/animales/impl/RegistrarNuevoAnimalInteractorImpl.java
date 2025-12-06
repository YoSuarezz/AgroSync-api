package com.agrosync.application.primaryports.interactor.animales.impl;

import com.agrosync.application.primaryports.dto.animales.request.RegistrarNuevoAnimalDTO;
import com.agrosync.application.primaryports.interactor.animales.RegistrarNuevoAnimalInteractor;
import com.agrosync.application.primaryports.mapper.animales.AnimalDTOMapper;
import com.agrosync.application.usecase.animales.RegistrarNuevoAnimal;
import com.agrosync.domain.animales.AnimalDomain;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RegistrarNuevoAnimalInteractorImpl implements RegistrarNuevoAnimalInteractor {

    private final RegistrarNuevoAnimal registrarNuevoAnimal;

    public RegistrarNuevoAnimalInteractorImpl(RegistrarNuevoAnimal registrarNuevoAnimal) {
        this.registrarNuevoAnimal = registrarNuevoAnimal;
    }

    @Override
    public void ejecutar(RegistrarNuevoAnimalDTO data) {
        AnimalDomain animalDomain = AnimalDTOMapper.INSTANCE.toDomain(data);
        registrarNuevoAnimal.ejecutar(animalDomain);
    }
}