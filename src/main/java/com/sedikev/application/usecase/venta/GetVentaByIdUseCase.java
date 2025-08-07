package com.sedikev.application.usecase.venta;

import com.sedikev.application.usecase.UseCaseWithReturn;
import com.sedikev.crosscutting.exception.custom.BusinessSedikevException;
import com.sedikev.domain.model.VentaDomain;
import com.sedikev.application.secondaryports.repository.VentaRepository;
import com.sedikev.application.primaryports.mapper.VentaMapper;
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
                .orElseThrow(() -> new BusinessSedikevException("Venta no encontrada con ID: " + id));
    }
}
