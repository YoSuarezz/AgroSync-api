package com.sedikev.domain.service;

import com.sedikev.domain.entity.Pago;

import java.util.List;

public interface PagoService {

    Pago save(Pago pago);

    Pago findById(Long id);

    void deleteById(Long id);

    List<Pago> findAll();
}
