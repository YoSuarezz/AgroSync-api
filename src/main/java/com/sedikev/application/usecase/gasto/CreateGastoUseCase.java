package com.sedikev.application.usecase.gasto;

import com.sedikev.application.usecase.UseCaseWithReturn;
import com.sedikev.crosscutting.exception.custom.BusinessSedikevException;
import com.sedikev.domain.model.GastoDomain;
import com.sedikev.domain.repository.GastoRepository;
import com.sedikev.application.mapper.GastoMapper;
import com.sedikev.infrastructure.adapter.entity.GastoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class CreateGastoUseCase implements UseCaseWithReturn<GastoDomain, GastoDomain> {

    private final GastoRepository gastoRepository;
    private final GastoMapper gastoMapper;

    @Override
    public GastoDomain ejecutar(GastoDomain gastoDomain) {
        // Validación de negocio: Verificar que la cantidad no sea negativa
        if (gastoDomain.getCantidad().compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessSedikevException("La cantidad del gasto no puede ser negativa");
        }
        if (gastoRepository.existsById(gastoDomain.getId())) {
            throw new BusinessSedikevException("El gasto ya existe");
        }
        if (gastoDomain.getLote() == null || gastoDomain.getLote().getId() == null) {
            throw new BusinessSedikevException("El gasto debe estar asociado a un lote");
        }

        // Mapear y guardar el gasto
        GastoEntity gastoEntity = gastoMapper.toEntity(gastoDomain);
        GastoEntity gastoSaved = gastoRepository.save(gastoEntity);
        return gastoMapper.toDomain(gastoSaved);
    }
}
