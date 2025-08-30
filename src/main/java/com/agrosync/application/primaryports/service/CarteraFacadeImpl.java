package com.agrosync.application.primaryports.service;

import com.agrosync.application.primaryports.mapper.CarteraMapper;
import com.agrosync.application.usecase.cartera.*;
import com.agrosync.crosscutting.exception.custom.BusinessAgroSyncException;
import com.agrosync.domain.model.CarteraDomain;
import com.agrosync.application.secondaryports.repository.CarteraRepository;
import com.agrosync.domain.service.CarteraService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarteraFacadeImpl implements CarteraService {

    private final CreateCarteraUseCase createCarteraUseCase;
    private final UpdateCarteraUseCase updateCarteraUseCase;
    private final DeleteCarteraUseCase deleteCarteraUseCase;
    private final GetCarteraByIdUseCase getCarteraByIdUseCase;
    private final GetAllCarterasUseCase getAllCarterasUseCase;

    private final CarteraRepository carteraRepository;
    private final CarteraMapper carteraMapper;

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

    @Override
    public CarteraDomain findByUserId(Long id) {
        return carteraRepository.findAll().stream()
                .filter(cartera -> cartera.getUsuario().getId().equals(id))
                .findFirst()
                .map(carteraMapper::toDomain)
                .orElseThrow(() -> new BusinessAgroSyncException("No se encontró cartera para el usuario con ID: " + id));
    }
}
