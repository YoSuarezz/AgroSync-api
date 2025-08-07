package com.sedikev.application.usecase.cartera;

import com.sedikev.crosscutting.exception.custom.BusinessSedikevException;
import com.sedikev.application.secondaryports.repository.CarteraRepository;
import com.sedikev.application.usecase.UseCaseWithoutReturn;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteCarteraUseCase implements UseCaseWithoutReturn<Long> {

    private final CarteraRepository carteraRepository;

    @Override
    public void ejecutar(Long id) {
        // Validar que la cartera exista
        if (!carteraRepository.existsById(id)) {
            throw new BusinessSedikevException("La cartera no existe");
        }

        // Eliminar la cartera
        carteraRepository.deleteById(id);
    }
}
