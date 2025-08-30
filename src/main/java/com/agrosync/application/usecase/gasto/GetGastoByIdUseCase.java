package com.agrosync.application.usecase.gasto;

import com.agrosync.application.usecase.UseCaseWithReturn;
import com.agrosync.crosscutting.exception.custom.BusinessAgroSyncException;
import com.agrosync.domain.model.GastoDomain;
import com.agrosync.application.secondaryports.repository.GastoRepository;
import com.agrosync.application.primaryports.mapper.GastoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetGastoByIdUseCase implements UseCaseWithReturn<Long, GastoDomain> {

    private final GastoRepository gastoRepository;
    private final GastoMapper gastoMapper;

    @Override
    public GastoDomain ejecutar(Long id) {
        // Mapear la entidad al dominio
        return gastoRepository.findById(id)
                .map(gastoMapper::toDomain)
                .orElseThrow(() -> new BusinessAgroSyncException("Gasto no encontrado con ID: " + id));
    }
}
