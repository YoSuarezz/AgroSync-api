package com.sedikev.domain.service;

import com.sedikev.application.domain.VentaDomain;
import com.sedikev.domain.entity.VentaEntity;

import java.util.List;

public interface VentaService {

    VentaDomain save(VentaDomain VentaDomain);

    VentaDomain findById(Long id);

    void deleteById(Long id);

    List<VentaDomain> findAll();
}
