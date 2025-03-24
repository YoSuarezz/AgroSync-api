package com.sedikev.domain.service;

import com.sedikev.domain.model.PagoDomain;

import java.util.List;

public interface PagoService {

    PagoDomain save(PagoDomain PagoDomain);

    PagoDomain update(PagoDomain PagoDomain);

    PagoDomain findById(Long id);

    void deleteById(Long id);

    List<PagoDomain> findAll();
}
