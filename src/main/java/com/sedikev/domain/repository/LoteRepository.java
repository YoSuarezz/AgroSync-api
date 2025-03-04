package com.sedikev.domain.repository;

import com.sedikev.domain.entity.LoteEntity;

import java.util.List;
import java.util.Optional;

public interface LoteRepository {

    LoteEntity save(LoteEntity loteEntity);

    Optional<LoteEntity> findById(Long id);

    void deleteById(Long id);

    List<LoteEntity> findAll();
}
