package com.agrosync.domain.carteras.rules.impl;

import com.agrosync.application.secondaryports.repository.CarteraRepository;
import com.agrosync.domain.carteras.exceptions.CarteraIdNoExisteException;
import com.agrosync.domain.carteras.rules.CarteraIdExisteRule;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CarteraIdExisteRuleImpl implements CarteraIdExisteRule {

    private final CarteraRepository carteraRepository;

    public CarteraIdExisteRuleImpl(CarteraRepository carteraRepository) {
        this.carteraRepository = carteraRepository;
    }

    @Override
    public void validate(UUID data) {
        if (!carteraRepository.existsById(data)) {
            throw CarteraIdNoExisteException.create();
        }
    }
}
