package com.sedikev.application.usecase.animal;

import com.sedikev.crosscutting.exception.custom.BusinessSedikevException;
import com.sedikev.application.secondaryports.repository.AnimalRepository;
import com.sedikev.application.usecase.UseCaseWithoutReturn;
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
            throw new BusinessSedikevException("El animal no existe");
        }

        // Eliminar el animal
        animalRepository.deleteById(id);
    }
}
