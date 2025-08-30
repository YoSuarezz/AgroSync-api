package com.agrosync.application.primaryports.service;

import com.agrosync.application.usecase.gasto.*;
import com.agrosync.domain.model.GastoDomain;
import com.agrosync.domain.service.GastoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GastoFacadeImpl implements GastoService {

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
