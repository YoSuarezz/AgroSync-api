package com.agrosync.application.usecase.cartera;

import com.agrosync.crosscutting.exception.custom.BusinessAgroSyncException;
import com.agrosync.application.secondaryports.repository.CarteraRepository;
import com.agrosync.application.usecase.UseCaseWithoutReturn;
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
            throw new BusinessAgroSyncException("La cartera no existe");
        }

        // Eliminar la cartera
        carteraRepository.deleteById(id);
    }
}
