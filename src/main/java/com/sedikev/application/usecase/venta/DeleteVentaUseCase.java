package com.sedikev.application.usecase.venta;

import com.sedikev.crosscutting.exception.custom.BusinessSedikevException;
import com.sedikev.domain.repository.VentaRepository;
import com.sedikev.application.usecase.UseCaseWithoutReturn;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteVentaUseCase implements UseCaseWithoutReturn<Long> {

    private final VentaRepository ventaRepository;

    @Override
    public void ejecutar(Long id) {
        // Validar que la venta exista
        if (!ventaRepository.existsById(id)) {
            throw new BusinessSedikevException("La venta no existe");
        }

        // Eliminar la venta
        ventaRepository.deleteById(id);
    }
}
