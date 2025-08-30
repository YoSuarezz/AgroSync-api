package com.agrosync.domain.service;

import com.agrosync.domain.model.GastoDomain;

import java.util.List;

public interface GastoService {

    GastoDomain save(GastoDomain gastoDomain);

    GastoDomain update(GastoDomain gastoDomain);

    GastoDomain findById(Long id);

    void deleteById(Long id);

    List<GastoDomain> findAll();
}
