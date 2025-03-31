package com.sedikev.application.service;

import com.sedikev.application.usecase.cartera.*;
import com.sedikev.domain.model.CarteraDomain;
import com.sedikev.domain.service.CarteraService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarteraFacadeImpl implements CarteraService {

    private final CreateCarteraUseCase createCarteraUseCase;
    private final UpdateCarteraUseCase updateCarteraUseCase;
    private final DeleteCarteraUseCase deleteCarteraUseCase;
    private final GetCarteraByIdUseCase getCarteraByIdUseCase;
    private final GetAllCarterasUseCase getAllCarterasUseCase;

    @Override
    public CarteraDomain save(CarteraDomain carteraDomain) {
        return createCarteraUseCase.ejecutar(carteraDomain);
    }

    @Override
    public CarteraDomain update(CarteraDomain carteraDomain) {
        return updateCarteraUseCase.ejecutar(carteraDomain);
    }

    @Override
    public void deleteById(Long id) {
        deleteCarteraUseCase.ejecutar(id);
    }

    @Override
    public CarteraDomain findById(Long id) {
        return getCarteraByIdUseCase.ejecutar(id);
    }

    @Override
    public List<CarteraDomain> findAll() {
        return getAllCarterasUseCase.ejecutar(null);
    }
}
