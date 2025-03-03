package com.sedikev.domain.service;

import com.sedikev.domain.entity.Gasto;

import java.util.List;

public interface GastoService {

    Gasto save(Gasto gasto);

    Gasto findById(Long id);

    void deleteById(Long id);

    List<Gasto> findAll();
}
