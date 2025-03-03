package com.sedikev.domain.repository;

import com.sedikev.domain.entity.Gasto;

import java.util.List;
import java.util.Optional;

public interface GastoRepository {

    Gasto save(Gasto gasto);

    Optional<Gasto> findById(Long id);

    void deleteById(Long id);

    List<Gasto> findAll();
}
