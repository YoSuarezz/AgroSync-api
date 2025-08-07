package com.agrosync.application.usecase.venta;

import com.agrosync.crosscutting.exception.custom.BusinessAgroSyncException;
import com.agrosync.application.secondaryports.repository.VentaRepository;
import com.agrosync.application.usecase.UseCaseWithoutReturn;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteVentaUseCase implements UseCaseWithoutReturn<Long> {

    private final VentaRepository ventaRepository;

    @Override
    public void ejecutar(Long id) {
        // Validar que la venta exista
        if (!ventaRepository.existsById(id)) {
            throw new BusinessAgroSyncException("La venta no existe");
        }

        // Eliminar la venta
        ventaRepository.deleteById(id);
    }
}
