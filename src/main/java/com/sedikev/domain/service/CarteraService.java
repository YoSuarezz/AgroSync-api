package com.sedikev.domain.service;

import com.sedikev.domain.entity.CarteraEntity;

import java.util.List;

public interface CarteraService {

    CarteraEntity save(CarteraEntity carteraEntity);

    CarteraEntity findById(Long id);

    void deleteById(Long id);

    List<CarteraEntity> findAll();
}
