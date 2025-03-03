package com.sedikev.domain.repository;

import com.sedikev.domain.entity.Pago;

import java.util.List;
import java.util.Optional;

public interface PagoRepository {

    Pago save(Pago pago);

    Optional<Pago> findById(Long id);

    void deleteById(Long id);

    List<Pago> findAll();
}
