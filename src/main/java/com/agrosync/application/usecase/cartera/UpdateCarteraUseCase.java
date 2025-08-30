package com.agrosync.application.usecase.cartera;

import com.agrosync.application.usecase.UseCaseWithReturn;
import com.agrosync.crosscutting.exception.custom.BusinessAgroSyncException;
import com.agrosync.domain.model.CarteraDomain;
import com.agrosync.application.secondaryports.repository.CarteraRepository;
import com.agrosync.application.primaryports.mapper.CarteraMapper;
import com.agrosync.application.secondaryports.entity.CarteraEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateCarteraUseCase implements UseCaseWithReturn<CarteraDomain, CarteraDomain> {

    private final CarteraRepository carteraRepository;
    private final CarteraMapper carteraMapper;

    @Override
    public CarteraDomain ejecutar(CarteraDomain carteraDomain) {
        // Validación de negocio: Verificar que la cartera exista
        if (!carteraRepository.existsById(carteraDomain.getId())) {
            throw new BusinessAgroSyncException("La cartera no existe");
        }
        if (carteraDomain.getUsuario() == null || carteraDomain.getUsuario().getId() == null) {
            throw new BusinessAgroSyncException("La cartera debe estar asociado a un usuario");
        }

        // Mapear y actualizar la cartera
        CarteraEntity carteraEntity = carteraMapper.toEntity(carteraDomain);
        CarteraEntity carteraUpdated = carteraRepository.save(carteraEntity);
        return carteraMapper.toDomain(carteraUpdated);
    }
}
