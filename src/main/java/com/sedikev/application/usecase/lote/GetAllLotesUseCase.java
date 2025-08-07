package com.sedikev.application.usecase.lote;

import com.sedikev.application.usecase.UseCaseWithReturn;
import com.sedikev.domain.model.LoteDomain;
import com.sedikev.application.secondaryports.repository.LoteRepository;
import com.sedikev.application.primaryports.mapper.LoteMapper;
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
