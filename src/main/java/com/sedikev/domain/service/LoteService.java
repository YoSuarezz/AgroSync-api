package com.sedikev.domain.service;

import com.sedikev.domain.model.LoteDomain;
import com.sedikev.infrastructure.adapter.entity.LoteEntity;

import java.util.List;
import java.util.Optional;

public interface LoteService {

    LoteDomain save(LoteDomain LoteDomain);

    LoteDomain update(LoteDomain LoteDomain);

    LoteDomain findById(Long id);

    void deleteById(Long id);

    List<LoteDomain> findAll();

    Optional<LoteEntity> findByContramarcaAndSemana(Integer contramarca, Integer semana);
}