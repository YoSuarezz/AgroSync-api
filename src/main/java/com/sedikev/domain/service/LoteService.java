package com.sedikev.domain.service;

import com.sedikev.domain.entity.LoteEntity;

import java.util.List;

public interface LoteService {

    LoteEntity save(LoteEntity loteEntity);

    LoteEntity findById(Long id);

    void deleteById(Long id);

    List<LoteEntity> findAll();
}
