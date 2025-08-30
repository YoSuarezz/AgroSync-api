package com.agrosync.application.usecase.animal;

import com.agrosync.crosscutting.exception.custom.BusinessAgroSyncException;
import com.agrosync.application.secondaryports.repository.AnimalRepository;
import com.agrosync.application.usecase.UseCaseWithoutReturn;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteAnimalUseCase implements UseCaseWithoutReturn<Long> {

    private final AnimalRepository animalRepository;

    @Override
    public void ejecutar(Long id) {
        // Validar que el animal exista
        if (!animalRepository.existsById(id)) {
            throw new BusinessAgroSyncException("El animal no existe");
        }

        // Eliminar el animal
        animalRepository.deleteById(id);
    }
}
