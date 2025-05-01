package com.sedikev.application.service;

import com.sedikev.application.mapper.CarteraMapper;
import com.sedikev.application.usecase.cartera.*;
import com.sedikev.crosscutting.exception.custom.BusinessSedikevException;
import com.sedikev.domain.model.CarteraDomain;
import com.sedikev.domain.repository.CarteraRepository;
import com.sedikev.domain.service.CarteraService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
                .orElseThrow(() -> new BusinessSedikevException("No se encontró cartera para el usuario con ID: " + id));
    }
}
