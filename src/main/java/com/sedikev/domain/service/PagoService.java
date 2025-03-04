package com.sedikev.domain.service;

import com.sedikev.domain.entity.PagoEntity;

import java.util.List;

public interface PagoService {

    PagoEntity save(PagoEntity pagoEntity);

    PagoEntity findById(Long id);

    void deleteById(Long id);

    List<PagoEntity> findAll();
}
