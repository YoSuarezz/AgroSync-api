package com.sedikev.domain.service;

import com.sedikev.domain.model.VentaDomain;

import java.util.List;

public interface VentaService {

    VentaDomain save(VentaDomain VentaDomain);

    VentaDomain update(VentaDomain VentaDomain);

    VentaDomain findById(Long id);

    void deleteById(Long id);

    List<VentaDomain> findAll();

    List<VentaDomain> findByClienteId(Long clienteId);
}
