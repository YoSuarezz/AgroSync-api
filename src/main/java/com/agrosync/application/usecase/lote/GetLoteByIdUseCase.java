package com.agrosync.application.usecase.lote;

import com.agrosync.application.usecase.UseCaseWithReturn;
import com.agrosync.crosscutting.exception.custom.BusinessAgroSyncException;
import com.agrosync.domain.model.LoteDomain;
import com.agrosync.application.secondaryports.repository.LoteRepository;
import com.agrosync.application.primaryports.mapper.LoteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetLoteByIdUseCase implements UseCaseWithReturn<Long, LoteDomain> {

    private final LoteRepository loteRepository;
    private final LoteMapper loteMapper;

    @Override
    public LoteDomain ejecutar(Long id) {
        // Buscar el lote por ID, mapear la entidad al dominio y lanzar excepción si no existe
        return loteRepository.findById(id)
                .map(loteMapper::toDomain)
                .orElseThrow(() -> new BusinessAgroSyncException("Lote no encontrado con ID: " + id));
    }
}
