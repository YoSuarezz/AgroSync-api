package com.agrosync.application.usecase.lote;

import com.agrosync.application.secondaryports.repository.LoteRepository;
import com.agrosync.application.primaryports.mapper.LoteMapper;
import com.agrosync.domain.model.LoteDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetLotesByProveedorIdUseCase {
    private final LoteRepository loteRepository;
    private final LoteMapper loteMapper;

    public List<LoteDomain> ejecutar(Long proveedorId) {
        return loteRepository.findByUsuarioId(proveedorId).stream()
                .map(loteMapper::toDomain)
                .collect(Collectors.toList());
    }
}
