package com.sedikev.domain.repository;

import com.sedikev.domain.entity.Lote;

import java.util.List;
import java.util.Optional;

public interface LoteRepository {

    Lote save(Lote lote);

    Optional<Lote> findById(Long id);

    void deleteById(Long id);

    List<Lote> findAll();
}
