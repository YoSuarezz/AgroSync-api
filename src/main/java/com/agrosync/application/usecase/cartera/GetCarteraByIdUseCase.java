package com.agrosync.application.usecase.cartera;

import com.agrosync.application.usecase.UseCaseWithReturn;
import com.agrosync.crosscutting.exception.custom.BusinessAgroSyncException;
import com.agrosync.domain.model.CarteraDomain;
import com.agrosync.application.secondaryports.repository.CarteraRepository;
import com.agrosync.application.primaryports.mapper.CarteraMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class GetCarteraByIdUseCase implements UseCaseWithReturn<Long, CarteraDomain> {

    private final CarteraRepository carteraRepository;
    private final CarteraMapper carteraMapper;

    @Override
    public CarteraDomain ejecutar(Long id) {

        return carteraRepository.findById(id)
                .map(carteraMapper::toDomain)
                .orElseThrow(() -> new BusinessAgroSyncException("cartera no encontrada con ID: " + id));
    }
}
