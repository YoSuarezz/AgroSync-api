package com.sedikev.application.usecase.cartera;

import com.sedikev.application.usecase.UseCaseWithReturn;
import com.sedikev.crosscutting.exception.custom.BusinessSedikevException;
import com.sedikev.domain.model.CarteraDomain;
import com.sedikev.domain.repository.CarteraRepository;
import com.sedikev.application.mapper.CarteraMapper;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class GetCarteraByIdUseCase implements UseCaseWithReturn<Long, CarteraDomain> {

    private final CarteraRepository carteraRepository;
    private final CarteraMapper carteraMapper;

    @Override
    public CarteraDomain ejecutar(Long id) {

        return carteraRepository.findById(id)
                .map(carteraMapper::toDomain)
                .orElseThrow(() -> new BusinessSedikevException("cartera no encontrada con ID: " + id));
    }
}
