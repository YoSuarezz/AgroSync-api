package com.sedikev.domain.service;

import com.sedikev.domain.entity.Lote;

import java.util.List;

public interface LoteService {

    Lote save(Lote lote);

    Lote findById(Long id);

    void deleteById(Long id);

    List<Lote> findAll();
}
