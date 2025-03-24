package com.sedikev.application.usecase.pago;

import com.sedikev.application.usecase.UseCaseWithReturn;
import com.sedikev.domain.model.PagoDomain;
import com.sedikev.domain.repository.PagoRepository;
import com.sedikev.application.mapper.PagoMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class GetAllPagosUseCase implements UseCaseWithReturn<Void, List<PagoDomain>> {

    private final PagoRepository pagoRepository;
    private final PagoMapper pagoMapper;

    @Override
    public List<PagoDomain> ejecutar(Void unused) {
        // Obtener todos los pagos
        return pagoRepository.findAll().stream()
                .map(pagoMapper::toDomain)
                .collect(Collectors.toList());
    }
}
