package com.agrosync.application.usecase.pago;

import com.agrosync.application.usecase.UseCaseWithReturn;
import com.agrosync.crosscutting.exception.custom.BusinessAgroSyncException;
import com.agrosync.domain.model.PagoDomain;
import com.agrosync.application.secondaryports.repository.PagoRepository;
import com.agrosync.application.primaryports.mapper.PagoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetPagoByIdUseCase implements UseCaseWithReturn<Long, PagoDomain> {

    private final PagoRepository pagoRepository;
    private final PagoMapper pagoMapper;

    @Override
    public PagoDomain ejecutar(Long id) {
        // Buscar el pago por ID, mapear la entidad al dominio y lanzar excepción si no existe
        return pagoRepository.findById(id)
                .map(pagoMapper::toDomain)
                .orElseThrow(() -> new BusinessAgroSyncException("Pago no encontrado con ID: " + id));
    }
}
