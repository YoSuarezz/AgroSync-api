package com.sedikev.domain.repository;

import com.sedikev.infrastructure.adapter.entity.VentaEntity;

import java.util.List;
import java.util.Optional;

public interface VentaRepository {

    VentaEntity save(VentaEntity ventaEntity);

    Optional<VentaEntity> findById(Long id);

    void deleteById(Long id);

    List<VentaEntity> findAll();
}
