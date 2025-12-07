package com.agrosync.domain.cuentaspagar.rules.impl;

import com.agrosync.application.secondaryports.repository.CuentaPagarRepository;
import com.agrosync.domain.cuentaspagar.exceptions.IdentificadorCuentaPagarNoExisteException;
import com.agrosync.domain.cuentaspagar.rules.IdentificadorCuentaPagarExisteRule;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class IdentificadorCuentaPagarExisteRuleImpl implements IdentificadorCuentaPagarExisteRule {

    private final CuentaPagarRepository cuentaPagarRepository;

    public IdentificadorCuentaPagarExisteRuleImpl(CuentaPagarRepository cuentaPagarRepository) {
        this.cuentaPagarRepository = cuentaPagarRepository;
    }

    @Override
    public void validate(UUID data) {
        boolean exists = cuentaPagarRepository.existsById(data);
        if (!exists) {
            throw IdentificadorCuentaPagarNoExisteException.create();
        }
    }
}
