package com.sedikev.application.service;

import com.sedikev.application.usecase.lote.*;
import com.sedikev.domain.model.LoteDomain;
import com.sedikev.domain.service.LoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoteFacadeImpl implements LoteService {

    private final CreateLoteUseCase createLoteUseCase;
    private final UpdateLoteUseCase updateLoteUseCase;
    private final DeleteLoteUseCase deleteLoteUseCase;
    private final GetLoteByIdUseCase getLoteByIdUseCase;
    private final GetAllLotesUseCase getAllLotesUseCase;

    @Override
    public LoteDomain save(LoteDomain loteDomain) {
        return createLoteUseCase.ejecutar(loteDomain);
    }

    @Override
    public LoteDomain update(LoteDomain loteDomain) {
        return updateLoteUseCase.ejecutar(loteDomain);
    }

    @Override
    public LoteDomain findById(Long id) {
        return getLoteByIdUseCase.ejecutar(id);
    }

    @Override
    public void deleteById(Long id) {
        deleteLoteUseCase.ejecutar(id);
    }

    @Override
    public List<LoteDomain> findAll() {
        return getAllLotesUseCase.ejecutar(null);
    }
}
