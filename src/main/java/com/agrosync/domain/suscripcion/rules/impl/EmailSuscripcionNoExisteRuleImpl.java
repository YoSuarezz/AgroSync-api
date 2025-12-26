package com.agrosync.domain.suscripcion.rules.impl;

import com.agrosync.application.secondaryports.repository.SuscripcionRepository;
import com.agrosync.crosscutting.helpers.TextHelper;
import com.agrosync.domain.suscripcion.exceptions.EmailSuscripcionExisteException;
import com.agrosync.domain.suscripcion.rules.EmailSuscripcionNoExisteRule;
import org.springframework.stereotype.Service;

@Service
public class EmailSuscripcionNoExisteRuleImpl implements EmailSuscripcionNoExisteRule {

    private final SuscripcionRepository suscripcionRepository;

    public EmailSuscripcionNoExisteRuleImpl(SuscripcionRepository suscripcionRepository) {
        this.suscripcionRepository = suscripcionRepository;
    }

    @Override
    public void validate(String email) {
        if (TextHelper.isEmpty(email)) {
            return;
        }

        if (suscripcionRepository.existsByEmailIgnoreCase(email)) {
            throw EmailSuscripcionExisteException.create();
        }
    }
}
