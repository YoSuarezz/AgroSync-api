package com.sedikev.domain.repository;

import com.sedikev.infrastructure.adapter.entity.PagoEntity;

import java.util.List;
import java.util.Optional;

public interface PagoRepository {

    PagoEntity save(PagoEntity pagoEntity);

    Optional<PagoEntity> findById(Long id);

    void deleteById(Long id);

    List<PagoEntity> findAll();
}
