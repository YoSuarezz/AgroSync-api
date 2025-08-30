package com.agrosync.application.usecase.gasto;

import com.agrosync.crosscutting.exception.custom.BusinessAgroSyncException;
import com.agrosync.application.secondaryports.repository.GastoRepository;
import com.agrosync.application.usecase.UseCaseWithoutReturn;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteGastoUseCase implements UseCaseWithoutReturn<Long> {

    private final GastoRepository gastoRepository;

    @Override
    public void ejecutar(Long id) {
        // Validar que el gasto exista
        if (!gastoRepository.existsById(id)) {
            throw new BusinessAgroSyncException("El gasto no existe");
        }

        // Eliminar el gasto
        gastoRepository.deleteById(id);
    }
}
