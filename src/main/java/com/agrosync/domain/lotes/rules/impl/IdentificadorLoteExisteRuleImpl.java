package com.agrosync.domain.lotes.rules.impl;

import com.agrosync.application.secondaryports.repository.LoteRepository;
import com.agrosync.domain.IdConSuscripcion;
import com.agrosync.domain.lotes.exceptions.IdentificadorLoteNoExisteException;
import com.agrosync.domain.lotes.rules.IdentificadorLoteExisteRule;
import org.springframework.stereotype.Service;

@Service
public class IdentificadorLoteExisteRuleImpl implements IdentificadorLoteExisteRule {

    private final LoteRepository loteRepository;

    public IdentificadorLoteExisteRuleImpl(LoteRepository loteRepository) {
        this.loteRepository = loteRepository;
    }

    @Override
    public void validate(IdConSuscripcion data) {
        boolean exists = loteRepository.existsByIdAndSuscripcion_Id(data.getId(), data.getSuscripcionId());
        if (!exists) {
            throw IdentificadorLoteNoExisteException.create();
        }
    }
}

