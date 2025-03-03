package com.sedikev.domain.repository;

import com.sedikev.domain.entity.Cartera;

import java.util.List;
import java.util.Optional;

public interface CarteraRepository {

    Cartera save(Cartera cartera);

    Optional<Cartera> findById(Long id);

    void deleteById(Long id);

    List<Cartera> findAll();
}
