package com.sedikev.application.usecase.venta;

import com.sedikev.domain.repository.VentaRepository;
import com.sedikev.domain.model.VentaDomain;
import com.sedikev.application.mapper.VentaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetVentasByClienteIdUseCase {

    private final VentaRepository ventaRepository;
    private final VentaMapper ventaMapper;

    /**
     * Devuelve todas las ventas cuyo usuario.id == clienteId
     */
    public List<VentaDomain> ejecutar(Long clienteId) {
        return ventaRepository
                .findByUsuarioId(clienteId)
                .stream()
                .map(ventaMapper::toDomain)
                .collect(Collectors.toList());
    }
}
