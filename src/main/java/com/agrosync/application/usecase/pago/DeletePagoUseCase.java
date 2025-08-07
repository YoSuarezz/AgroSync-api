package com.agrosync.application.usecase.pago;

import com.agrosync.crosscutting.exception.custom.BusinessAgroSyncException;
import com.agrosync.application.secondaryports.repository.PagoRepository;
import com.agrosync.application.usecase.UseCaseWithoutReturn;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeletePagoUseCase implements UseCaseWithoutReturn<Long> {

    private final PagoRepository pagoRepository;

    @Override
    public void ejecutar(Long id) {
        // Validar que el pago exista
        if (!pagoRepository.existsById(id)) {
            throw new BusinessAgroSyncException("El pago no existe");
        }

        // Eliminar el pago
        pagoRepository.deleteById(id);
    }
}
