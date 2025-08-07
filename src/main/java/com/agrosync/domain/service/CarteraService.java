package com.agrosync.domain.service;

import com.agrosync.domain.model.CarteraDomain;

import java.util.List;

public interface CarteraService {

    CarteraDomain save(CarteraDomain carteraDomain);

    CarteraDomain update(CarteraDomain carteraDomain);

    CarteraDomain findById(Long id);

    void deleteById(Long id);

    List<CarteraDomain> findAll();

    public CarteraDomain findByUserId(Long id);
}
