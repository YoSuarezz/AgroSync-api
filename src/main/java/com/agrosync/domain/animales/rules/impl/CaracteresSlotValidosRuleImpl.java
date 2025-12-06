package com.agrosync.domain.animales.rules.impl;

import com.agrosync.domain.animales.exceptions.CaracteresSlotNoValidosException;
import com.agrosync.domain.animales.rules.CaracteresSlotValidosRule;
import org.springframework.stereotype.Service;

@Service
public class CaracteresSlotValidosRuleImpl implements CaracteresSlotValidosRule {

    private static final int MAX_LENGTH = 20;

    @Override
    public void validate(String slot) {
        if (slot == null || slot.trim().isEmpty() || slot.length() > MAX_LENGTH) {
            throw CaracteresSlotNoValidosException.create();
        }
    }
}