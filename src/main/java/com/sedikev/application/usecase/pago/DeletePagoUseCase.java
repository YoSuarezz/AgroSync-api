package com.sedikev.application.usecase.pago;

import com.sedikev.crosscutting.exception.custom.BusinessSedikevException;
import com.sedikev.domain.repository.PagoRepository;
import com.sedikev.application.usecase.UseCaseWithoutReturn;
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
            throw new BusinessSedikevException("El pago no existe");
        }

        // Eliminar el pago
        pagoRepository.deleteById(id);
    }
}
