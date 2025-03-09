package com.sedikev.domain.service;

import com.sedikev.application.domain.LoteDomain;
import com.sedikev.domain.entity.LoteEntity;

import java.util.List;

public interface LoteService {

    LoteDomain save(LoteDomain LoteDomain);

    LoteDomain findById(Long id);

    void deleteById(Long id);

    List<LoteDomain> findAll();
}
