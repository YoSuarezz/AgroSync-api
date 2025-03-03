package com.sedikev.domain.service;

import com.sedikev.domain.entity.Cartera;

import java.util.List;

public interface CarteraService {

    Cartera save(Cartera cartera);

    Cartera findById(Long id);

    void deleteById(Long id);

    List<Cartera> findAll();
}
