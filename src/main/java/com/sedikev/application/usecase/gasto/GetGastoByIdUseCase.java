package com.sedikev.application.usecase.gasto;

import com.sedikev.application.usecase.UseCaseWithReturn;
import com.sedikev.crosscutting.exception.custom.BusinessSedikevException;
import com.sedikev.domain.model.GastoDomain;
import com.sedikev.application.secondaryports.repository.GastoRepository;
import com.sedikev.application.primaryports.mapper.GastoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetGastoByIdUseCase implements UseCaseWithReturn<Long, GastoDomain> {

    private final GastoRepository gastoRepository;
    private final GastoMapper gastoMapper;

    @Override
    public GastoDomain ejecutar(Long id) {
        // Mapear la entidad al dominio
        return gastoRepository.findById(id)
                .map(gastoMapper::toDomain)
                .orElseThrow(() -> new BusinessSedikevException("Gasto no encontrado con ID: " + id));
    }
}
