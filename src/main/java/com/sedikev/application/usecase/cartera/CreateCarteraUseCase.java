package com.sedikev.application.usecase.cartera;

import com.sedikev.application.mapper.CarteraMapper;
import com.sedikev.application.usecase.UseCaseWithReturn;
import com.sedikev.crosscutting.exception.custom.BusinessSedikevException;
import com.sedikev.domain.model.CarteraDomain;
import com.sedikev.domain.repository.CarteraRepository;
import com.sedikev.infrastructure.adapter.entity.CarteraEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class CreateCarteraUseCase implements UseCaseWithReturn<CarteraDomain, CarteraDomain> {

    private final CarteraRepository carteraRepository;
    private final CarteraMapper carteraMapper;

    @Override
    public CarteraDomain ejecutar(CarteraDomain carteraDomain) {

        // Mapear y guardar la cartera
        CarteraEntity carteraEntity = carteraMapper.toEntity(carteraDomain);
        CarteraEntity carteraSaved = carteraRepository.save(carteraEntity);
        return carteraMapper.toDomain(carteraSaved);
    }
}
