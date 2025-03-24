package com.sedikev.application.usecase.animal;

import com.sedikev.crosscutting.exception.custom.BusinessSedikevException;
import com.sedikev.domain.repository.AnimalRepository;
import com.sedikev.application.usecase.UseCaseWithoutReturn;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteAnimalUseCase implements UseCaseWithoutReturn<String> {

    private final AnimalRepository animalRepository;

    @Override
    public void ejecutar(String id) {
        // Validar que el animal exista
        if (!animalRepository.existsById(id)) {
            throw new BusinessSedikevException("El animal no existe");
        }

        // Eliminar el animal
        animalRepository.deleteById(id);
    }
}
