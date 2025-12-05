package com.agrosync.domain.suscripcion.rules.impl;

import com.agrosync.application.secondaryports.repository.SuscripcionRepository;
import com.agrosync.crosscutting.helpers.UUIDHelper;
import com.agrosync.domain.suscripcion.exceptions.SuscripcionNoExisteException;
import com.agrosync.domain.suscripcion.rules.SuscripcionExisteRule;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SuscripcionExisteRuleImpl implements SuscripcionExisteRule {

    private final SuscripcionRepository suscripcionRepository;

    public SuscripcionExisteRuleImpl(SuscripcionRepository suscripcionRepository) {
        this.suscripcionRepository = suscripcionRepository;
    }

    @Override
    public void validate(UUID data) {
        if (UUIDHelper.isDefault(data) || !suscripcionRepository.existsById(data)) {
            throw SuscripcionNoExisteException.create();
        }
    }
}
