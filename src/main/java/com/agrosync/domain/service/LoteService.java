package com.agrosync.domain.service;

import com.agrosync.domain.model.LoteDomain;
import com.agrosync.application.secondaryports.entity.LoteEntity;

import java.util.List;
import java.util.Optional;

public interface LoteService {

    LoteDomain save(LoteDomain LoteDomain);

    LoteDomain update(LoteDomain LoteDomain);

    LoteDomain findById(Long id);

    void deleteById(Long id);

    List<LoteDomain> findAll();

    Optional<LoteEntity> findByContramarcaAndSemana(Integer contramarca, Integer semana);
  
    List<LoteDomain> findByProveedorId(Long proveedorId);
}