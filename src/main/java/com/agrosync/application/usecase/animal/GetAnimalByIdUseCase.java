package com.agrosync.application.usecase.animal;

import com.agrosync.application.primaryports.mapper.AnimalMapper;
import com.agrosync.crosscutting.exception.custom.BusinessAgroSyncException;
import com.agrosync.domain.model.AnimalDomain;
import com.agrosync.application.secondaryports.repository.AnimalRepository;
import com.agrosync.application.usecase.UseCaseWithReturn;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetAnimalByIdUseCase implements UseCaseWithReturn<Long, AnimalDomain> {

    private final AnimalRepository animalRepository;
    private final AnimalMapper animalMapper;

    @Override
    public AnimalDomain ejecutar(Long id) {

        // Mapear la entidad al dominio
        return animalRepository.findById(id)
                .map(animalMapper::toDomain)
                .orElseThrow(() -> new BusinessAgroSyncException("Animal no encontrado con ID: " + id));
    }
}
