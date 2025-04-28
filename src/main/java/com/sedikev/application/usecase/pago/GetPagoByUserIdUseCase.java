package com.sedikev.application.usecase.pago;

import com.sedikev.domain.model.PagoDomain;
import com.sedikev.domain.repository.PagoRepository;
import com.sedikev.application.mapper.PagoMapper;
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
