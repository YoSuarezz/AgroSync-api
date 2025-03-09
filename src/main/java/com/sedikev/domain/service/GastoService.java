package com.sedikev.domain.service;

import com.sedikev.domain.model.GastoDomain;

import java.util.List;

public interface GastoService {

    GastoDomain save(GastoDomain gastoDomain);

    GastoDomain findById(Long id);

    void deleteById(Long id);

    List<GastoDomain> findAll();
}
