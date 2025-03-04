package com.sedikev.domain.service;

import com.sedikev.domain.entity.VentaEntity;

import java.util.List;

public interface VentaService {

    VentaEntity save(VentaEntity ventaEntity);

    VentaEntity findById(Long id);

    void deleteById(Long id);

    List<VentaEntity> findAll();
}
