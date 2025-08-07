package com.agrosync.application.usecase.lote;

import com.agrosync.application.usecase.UseCaseWithReturn;
import com.agrosync.domain.model.LoteDomain;
import com.agrosync.application.secondaryports.repository.LoteRepository;
import com.agrosync.application.primaryports.mapper.LoteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetAllLotesUseCase implements UseCaseWithReturn<Void, List<LoteDomain>> {

    private final LoteRepository loteRepository;
    private final LoteMapper loteMapper;

    @Override
    public List<LoteDomain> ejecutar(Void unused) {
        // Obtener todos los lotes
        return loteRepository.findAll().stream()
                .map(loteMapper::toDomain)
                .collect(Collectors.toList());
    }
}
