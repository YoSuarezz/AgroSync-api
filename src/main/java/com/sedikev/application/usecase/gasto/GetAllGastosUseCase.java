package com.sedikev.application.usecase.gasto;

import com.sedikev.application.usecase.UseCaseWithReturn;
import com.sedikev.domain.model.GastoDomain;
import com.sedikev.application.secondaryports.repository.GastoRepository;
import com.sedikev.application.primaryports.mapper.GastoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetAllGastosUseCase implements UseCaseWithReturn<Void, List<GastoDomain>> {

    private final GastoRepository gastoRepository;
    private final GastoMapper gastoMapper;

    @Override
    public List<GastoDomain> ejecutar(Void unused) {
        // Obtener todos los gastos
        return gastoRepository.findAll().stream()
                .map(gastoMapper::toDomain)
                .collect(Collectors.toList());
    }
}
