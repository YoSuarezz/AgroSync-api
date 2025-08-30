package com.agrosync.application.usecase.cartera;

import com.agrosync.application.primaryports.mapper.CarteraMapper;
import com.agrosync.application.usecase.UseCaseWithReturn;
import com.agrosync.domain.model.CarteraDomain;
import com.agrosync.application.secondaryports.repository.CarteraRepository;
import com.agrosync.application.secondaryports.entity.CarteraEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
