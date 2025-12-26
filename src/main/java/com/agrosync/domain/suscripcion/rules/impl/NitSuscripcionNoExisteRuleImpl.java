package com.agrosync.domain.suscripcion.rules.impl;

import com.agrosync.application.secondaryports.repository.SuscripcionRepository;
import com.agrosync.crosscutting.helpers.TextHelper;
import com.agrosync.domain.suscripcion.exceptions.NitSuscripcionExisteException;
import com.agrosync.domain.suscripcion.rules.NitSuscripcionNoExisteRule;
import org.springframework.stereotype.Service;

@Service
public class NitSuscripcionNoExisteRuleImpl implements NitSuscripcionNoExisteRule {

    private final SuscripcionRepository suscripcionRepository;

    public NitSuscripcionNoExisteRuleImpl(SuscripcionRepository suscripcionRepository) {
        this.suscripcionRepository = suscripcionRepository;
    }

    @Override
    public void validate(String nit) {
        if (TextHelper.isEmpty(nit)) {
            return;
        }

        if (suscripcionRepository.existsByNitIgnoreCase(nit)) {
            throw NitSuscripcionExisteException.create();
        }
    }
}
