package com.agrosync.domain.compras.rules.impl;

import com.agrosync.application.secondaryports.repository.CompraRepository;
import com.agrosync.domain.IdConSuscripcion;
import com.agrosync.domain.compras.exceptions.IdentificadorCompraNoExisteException;
import com.agrosync.domain.compras.rules.IdentificadorCompraExisteRule;
import org.springframework.stereotype.Service;

@Service
public class IdentificadorCompraExisteRuleImpl implements IdentificadorCompraExisteRule {

    private final CompraRepository compraRepository;

    public IdentificadorCompraExisteRuleImpl(CompraRepository compraRepository) {
        this.compraRepository = compraRepository;
    }

    @Override
    public void validate(IdConSuscripcion data) {
        boolean exists = compraRepository.existsByIdAndSuscripcion_Id(data.getId(), data.getSuscripcionId());
        if (!exists) {
            throw IdentificadorCompraNoExisteException.create();
        }
    }
}

