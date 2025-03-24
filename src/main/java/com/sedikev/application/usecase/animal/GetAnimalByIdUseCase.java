package com.sedikev.application.usecase.animal;

import com.sedikev.application.mapper.AnimalMapper;
import com.sedikev.crosscutting.exception.custom.BusinessSedikevException;
import com.sedikev.domain.model.AnimalDomain;
import com.sedikev.domain.repository.AnimalRepository;
import com.sedikev.application.usecase.UseCaseWithReturn;
import com.sedikev.infrastructure.adapter.entity.AnimalEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class GetAnimalByIdUseCase implements UseCaseWithReturn<String, AnimalDomain> {

    private final AnimalRepository animalRepository;
    private final AnimalMapper animalMapper;

    @Override
    public AnimalDomain ejecutar(String id) {

        // Mapear la entidad al dominio
        return animalRepository.findById(id)
                .map(animalMapper::toDomain)
                .orElseThrow(() -> new EntityNotFoundException("Animal no encontrado con ID: " + id));
    }
}
