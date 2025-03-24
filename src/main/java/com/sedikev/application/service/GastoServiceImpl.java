package com.sedikev.application.service;

import com.sedikev.application.usecase.gasto.*;
import com.sedikev.domain.model.GastoDomain;
import com.sedikev.domain.service.GastoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GastoServiceImpl implements GastoService {

    private final CreateGastoUseCase createGastoUseCase;
    private final UpdateGastoUseCase updateGastoUseCase;
    private final DeleteGastoUseCase deleteGastoUseCase;
    private final GetGastoByIdUseCase getGastoByIdUseCase;
    private final GetAllGastosUseCase getAllGastosUseCase;

    @Override
    public GastoDomain save(GastoDomain gastoDomain) {
        return createGastoUseCase.ejecutar(gastoDomain);
    }

    @Override
    public GastoDomain update(GastoDomain gastoDomain) {
        return updateGastoUseCase.ejecutar(gastoDomain);
    }

    @Override
    public void deleteById(Long id) {
        deleteGastoUseCase.ejecutar(id);
    }

    @Override
    public GastoDomain findById(Long id) {
        return getGastoByIdUseCase.ejecutar(id);
    }

    @Override
    public List<GastoDomain> findAll() {
        return getAllGastosUseCase.ejecutar(null);
    }
}
