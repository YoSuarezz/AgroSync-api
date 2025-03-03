package com.sedikev.domain.service;

import com.sedikev.domain.entity.Venta;

import java.util.List;

public interface VentaService {

    Venta save(Venta venta);

    Venta findById(Long id);

    void deleteById(Long id);

    List<Venta> findAll();
}
