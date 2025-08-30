package com.agrosync.application.usecase.pago;

import com.agrosync.domain.model.PagoDomain;
import com.agrosync.application.secondaryports.repository.PagoRepository;
import com.agrosync.application.primaryports.mapper.PagoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetPagoByUserIdUseCase {

    private final PagoRepository pagoRepository;
    private final PagoMapper pagoMapper;

    public List<PagoDomain> ejecutar(Long usuarioId) {
        // Recupera todos los pagos asociados a un usuarioId específico
        return pagoRepository.findByVentaUsuarioId(usuarioId).stream()
                .map(pagoMapper::toDomain)  // Mapea de PagoEntity a PagoDomain
                .collect(Collectors.toList());
    }
}
