package com.sedikev.application.service;

import com.sedikev.application.usecase.pago.*;
import com.sedikev.domain.model.PagoDomain;
import com.sedikev.domain.service.PagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PagoFacadeImpl implements PagoService {

    private final CreatePagoUseCase createPagoUseCase;
    private final UpdatePagoUseCase updatePagoUseCase;
    private final DeletePagoUseCase deletePagoUseCase;
    private final GetPagoByIdUseCase getPagoByIdUseCase;
    private final GetAllPagosUseCase getAllPagosUseCase;

    @Override
    public PagoDomain save(PagoDomain pagoDomain) {
        return createPagoUseCase.ejecutar(pagoDomain);
    }

    @Override
    public PagoDomain update(PagoDomain pagoDomain) {
        return updatePagoUseCase.ejecutar(pagoDomain);
    }

    @Override
    public PagoDomain findById(Long id) {
        return getPagoByIdUseCase.ejecutar(id);
    }

    @Override
    public void deleteById(Long id) {
        deletePagoUseCase.ejecutar(id);
    }

    @Override
    public List<PagoDomain> findAll() {
        return getAllPagosUseCase.ejecutar(null);
    }
}
