package com.agrosync.domain.ventas.rules.impl;

import com.agrosync.application.primaryports.dto.ventas.request.VentaIdSuscripcionDTO;
import com.agrosync.application.secondaryports.repository.VentaRepository;
import com.agrosync.domain.ventas.exceptions.IdentificadorVentaNoExisteException;
import com.agrosync.domain.ventas.rules.IdentificadorVentaExisteRule;
import org.springframework.stereotype.Service;

@Service
public class IdentificadorVentaExisteRuleImpl implements IdentificadorVentaExisteRule {

    private final VentaRepository ventaRepository;

    public IdentificadorVentaExisteRuleImpl(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }

    @Override
    public void validate(VentaIdSuscripcionDTO data) {
        boolean exists = ventaRepository.existsByIdAndSuscripcion_Id(data.getId(), data.getSuscripcionId());
        if (!exists) {
            throw IdentificadorVentaNoExisteException.create();
        }
    }
}
