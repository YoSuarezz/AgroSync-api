package com.agrosync.domain.lotes.rules.impl;

import com.agrosync.domain.animales.AnimalDomain;
import com.agrosync.domain.lotes.exceptions.ListaAnimalesVaciaException;
import com.agrosync.domain.lotes.rules.AnimalesLoteMinimoRule;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalesLoteMinimoRuleImpl implements AnimalesLoteMinimoRule {

    @Override
    public void validate(List<AnimalDomain> animales) {
        if (animales == null || animales.isEmpty()) {
            throw ListaAnimalesVaciaException.create();
        }
    }
}