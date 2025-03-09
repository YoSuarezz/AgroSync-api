package com.sedikev.domain.service;

import com.sedikev.application.domain.PagoDomain;
import com.sedikev.domain.entity.PagoEntity;

import java.util.List;

public interface PagoService {

    PagoDomain save(PagoDomain PagoDomain);

    PagoDomain findById(Long id);

    void deleteById(Long id);

    List<PagoDomain> findAll();
}
