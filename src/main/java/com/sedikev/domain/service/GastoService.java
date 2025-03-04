package com.sedikev.domain.service;

import com.sedikev.domain.entity.GastoEntity;

import java.util.List;

public interface GastoService {

    GastoEntity save(GastoEntity gastoEntity);

    GastoEntity findById(Long id);

    void deleteById(Long id);

    List<GastoEntity> findAll();
}
