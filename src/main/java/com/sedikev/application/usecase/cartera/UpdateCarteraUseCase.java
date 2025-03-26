package com.sedikev.application.usecase.cartera;

import com.sedikev.application.usecase.UseCaseWithReturn;
import com.sedikev.crosscutting.exception.custom.BusinessSedikevException;
import com.sedikev.domain.model.CarteraDomain;
import com.sedikev.domain.repository.CarteraRepository;
import com.sedikev.application.mapper.CarteraMapper;
import com.sedikev.infrastructure.adapter.entity.CarteraEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class UpdateCarteraUseCase implements UseCaseWithReturn<CarteraDomain, CarteraDomain> {

    private final CarteraRepository carteraRepository;
    private final CarteraMapper carteraMapper;

    @Override
    public CarteraDomain ejecutar(CarteraDomain carteraDomain) {
        // Validación de negocio: Verificar que la cartera exista
        if (!carteraRepository.existsById(carteraDomain.getId())) {
            throw new BusinessSedikevException("La cartera no existe");
        }
        if (carteraDomain.getUsuario() == null || carteraDomain.getUsuario().getId() == null) {
            throw new BusinessSedikevException("La cartera debe estar asociado a un usuario");
        }

        // Mapear y actualizar la cartera
        CarteraEntity carteraEntity = carteraMapper.toEntity(carteraDomain);
        CarteraEntity carteraUpdated = carteraRepository.save(carteraEntity);
        return carteraMapper.toDomain(carteraUpdated);
    }
}
