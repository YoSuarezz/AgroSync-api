package com.sedikev.application.service;

import com.sedikev.application.usecase.pago.*;
import com.sedikev.domain.model.PagoDomain;
import com.sedikev.domain.service.PagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PagoFacadeImpl implements PagoService {

    private final CreatePagoUseCase createPagoUseCase;
    private final UpdatePagoUseCase updatePagoUseCase;
    private final DeletePagoUseCase deletePagoUseCase;
    private final GetPagoByIdUseCase getPagoByIdUseCase;
    private final GetAllPagosUseCase getAllPagosUseCase;
    private final GetPagoByUserIdUseCase getPagoByUserIdUseCase;  // Inyectamos el nuevo caso de uso

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

    public List<PagoDomain> findByUsuarioId(Long usuarioId) {
        // Llamamos al nuevo caso de uso que obtiene los pagos por usuarioId
        return getPagoByUserIdUseCase.ejecutar(usuarioId);
    }
}
