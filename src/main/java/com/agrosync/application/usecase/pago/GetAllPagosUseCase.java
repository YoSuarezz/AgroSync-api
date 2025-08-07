package com.agrosync.application.usecase.pago;

import com.agrosync.application.usecase.UseCaseWithReturn;
import com.agrosync.domain.model.PagoDomain;
import com.agrosync.application.secondaryports.repository.PagoRepository;
import com.agrosync.application.primaryports.mapper.PagoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
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
