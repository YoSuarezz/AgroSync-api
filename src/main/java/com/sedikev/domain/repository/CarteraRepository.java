package com.sedikev.domain.repository;

import com.sedikev.domain.entity.CarteraEntity;

import java.util.List;
import java.util.Optional;

public interface CarteraRepository {

    CarteraEntity save(CarteraEntity carteraEntity);

    Optional<CarteraEntity> findById(Long id);

    void deleteById(Long id);

    List<CarteraEntity> findAll();
}
