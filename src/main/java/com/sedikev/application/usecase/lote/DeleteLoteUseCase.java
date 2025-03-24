package com.sedikev.application.usecase.lote;

import com.sedikev.crosscutting.exception.custom.BusinessSedikevException;
import com.sedikev.domain.repository.LoteRepository;
import com.sedikev.application.usecase.UseCaseWithoutReturn;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteLoteUseCase implements UseCaseWithoutReturn<Long> {

    private final LoteRepository loteRepository;

    @Override
    public void ejecutar(Long id) {
        // Validar que el lote exista
        if (!loteRepository.existsById(id)) {
            throw new BusinessSedikevException("El lote no existe");
        }

        // Eliminar el lote
        loteRepository.deleteById(id);
    }
}
