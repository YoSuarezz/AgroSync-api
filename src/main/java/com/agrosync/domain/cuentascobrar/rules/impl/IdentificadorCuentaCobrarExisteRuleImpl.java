package com.agrosync.domain.cuentascobrar.rules.impl;


import com.agrosync.application.secondaryports.repository.CuentaCobrarRepository;
import com.agrosync.domain.cuentascobrar.exceptions.IdentificadorCuentaCobrarNoExisteException;
import com.agrosync.domain.cuentascobrar.rules.IdentificadorCuentaCobrarExisteRule;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class IdentificadorCuentaCobrarExisteRuleImpl implements IdentificadorCuentaCobrarExisteRule {

    private final CuentaCobrarRepository cuentaCobrarRepository;

    public IdentificadorCuentaCobrarExisteRuleImpl(CuentaCobrarRepository cuentaCobrarRepository) {
        this.cuentaCobrarRepository = cuentaCobrarRepository;
    }

    @Override
    public void validate(UUID data) {
        boolean exists = cuentaCobrarRepository.existsById(data);
        if (!exists) {
            throw IdentificadorCuentaCobrarNoExisteException.create();
        }
    }
}
