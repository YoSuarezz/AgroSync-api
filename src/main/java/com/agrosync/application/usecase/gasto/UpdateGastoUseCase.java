package com.agrosync.application.usecase.gasto;

import com.agrosync.application.usecase.UseCaseWithReturn;
import com.agrosync.crosscutting.exception.custom.BusinessAgroSyncException;
import com.agrosync.domain.model.GastoDomain;
import com.agrosync.application.secondaryports.repository.GastoRepository;
import com.agrosync.application.primaryports.mapper.GastoMapper;
import com.agrosync.application.secondaryports.entity.GastoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class UpdateGastoUseCase implements UseCaseWithReturn<GastoDomain, GastoDomain> {

    private final GastoRepository gastoRepository;
    private final GastoMapper gastoMapper;

    @Override
    public GastoDomain ejecutar(GastoDomain gastoDomain) {
        // Validación de negocio: Verificar que el gasto exista
        if (!gastoRepository.existsById(gastoDomain.getId())) {
            throw new BusinessAgroSyncException("El gasto no existe");
        }
        // Validación de negocio: Verificar que la cantidad no sea negativa
        if (gastoDomain.getCantidad().compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessAgroSyncException("La cantidad del gasto no puede ser negativa");
        }
        if (gastoDomain.getLote() == null || gastoDomain.getLote().getId() == null) {
            throw new BusinessAgroSyncException("El gasto debe estar asociado a un lote");
        }


        // Mapear y actualizar el gasto
        GastoEntity gastoEntity = gastoMapper.toEntity(gastoDomain);
        GastoEntity gastoUpdated = gastoRepository.save(gastoEntity);
        return gastoMapper.toDomain(gastoUpdated);
    }
}
