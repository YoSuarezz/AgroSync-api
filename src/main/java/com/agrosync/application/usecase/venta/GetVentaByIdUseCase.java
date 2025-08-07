package com.agrosync.application.usecase.venta;

import com.agrosync.application.usecase.UseCaseWithReturn;
import com.agrosync.crosscutting.exception.custom.BusinessAgroSyncException;
import com.agrosync.domain.model.VentaDomain;
import com.agrosync.application.secondaryports.repository.VentaRepository;
import com.agrosync.application.primaryports.mapper.VentaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetVentaByIdUseCase implements UseCaseWithReturn<Long, VentaDomain> {

    private final VentaRepository ventaRepository;
    private final VentaMapper ventaMapper;

    @Override
    public VentaDomain ejecutar(Long id) {
        // Buscar la venta por ID, mapear la entidad al dominio y lanzar excepción si no existe
        return ventaRepository.findById(id)
                .map(ventaMapper::toDomain)
                .orElseThrow(() -> new BusinessAgroSyncException("Venta no encontrada con ID: " + id));
    }
}
