package com.sedikev.application.usecase.venta;

import com.sedikev.application.usecase.UseCaseWithReturn;
import com.sedikev.domain.model.VentaDomain;
import com.sedikev.application.secondaryports.repository.VentaRepository;
import com.sedikev.application.primaryports.mapper.VentaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetAllVentasUseCase implements UseCaseWithReturn<Void, List<VentaDomain>> {

    private final VentaRepository ventaRepository;
    private final VentaMapper ventaMapper;

    @Override
    public List<VentaDomain> ejecutar(Void unused) {
        // Obtener todas las ventas
        return ventaRepository.findAll().stream()
                .map(ventaMapper::toDomain)
                .collect(Collectors.toList());
    }
}
