package com.sedikev.domain.repository;

import com.sedikev.domain.entity.Venta;

import java.util.List;
import java.util.Optional;

public interface VentaRepository {

    Venta save(Venta venta);

    Optional<Venta> findById(Long id);

    void deleteById(Long id);

    List<Venta> findAll();
}
