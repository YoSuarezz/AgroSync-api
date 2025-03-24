package com.sedikev.application.usecase.cartera;

import com.sedikev.application.mapper.CarteraMapper;
import com.sedikev.application.usecase.UseCaseWithReturn;
import com.sedikev.crosscutting.exception.custom.BusinessSedikevException;
import com.sedikev.domain.model.CarteraDomain;
import com.sedikev.domain.repository.CarteraRepository;
import com.sedikev.infrastructure.adapter.entity.CarteraEntity;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class CreateCarteraUseCase implements UseCaseWithReturn<CarteraDomain, CarteraDomain> {

    private final CarteraRepository carteraRepository;
    private final CarteraMapper carteraMapper;

    @Override
    public CarteraDomain ejecutar(CarteraDomain carteraDomain) {

        if (carteraRepository.existsById(carteraDomain.getId())) {
            throw new BusinessSedikevException("La cartera ya existe");
        }


        // Mapear y guardar la cartera
        CarteraEntity carteraEntity = carteraMapper.toEntity(carteraDomain);
        CarteraEntity carteraSaved = carteraRepository.save(carteraEntity);
        return carteraMapper.toDomain(carteraSaved);
    }
}
