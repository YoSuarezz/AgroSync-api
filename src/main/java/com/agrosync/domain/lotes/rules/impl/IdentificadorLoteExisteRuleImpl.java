package com.agrosync.domain.lotes.rules.impl;

import com.agrosync.application.secondaryports.repository.LoteRepository;
import com.agrosync.domain.lotes.exceptions.IdentificadorLoteNoExisteException;
import com.agrosync.domain.lotes.rules.IdentificadorLoteExisteRule;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class IdentificadorLoteExisteRuleImpl implements IdentificadorLoteExisteRule {

    private final LoteRepository loteRepository;

    public IdentificadorLoteExisteRuleImpl(LoteRepository loteRepository) {
        this.loteRepository = loteRepository;
    }

    @Override
    public void validate(UUID id) {

        boolean exists = loteRepository.existsById(id);
        if (!exists) {
            throw IdentificadorLoteNoExisteException.create();
        }
    }
}
