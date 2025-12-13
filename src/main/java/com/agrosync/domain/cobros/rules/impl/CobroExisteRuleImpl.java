package com.agrosync.domain.cobros.rules.impl;

import com.agrosync.application.primaryports.dto.cobros.request.CobroIdSuscripcionDTO;
import com.agrosync.application.secondaryports.repository.CobroRepository;
import com.agrosync.domain.cobros.exceptions.CobroNoExisteException;
import com.agrosync.domain.cobros.rules.CobroExisteRule;
import org.springframework.stereotype.Service;

@Service
public class CobroExisteRuleImpl implements CobroExisteRule {

    private final CobroRepository cobroRepository;

    public CobroExisteRuleImpl(CobroRepository cobroRepository) {
        this.cobroRepository = cobroRepository;
    }

    @Override
    public void validate(CobroIdSuscripcionDTO data) {
        boolean exists = cobroRepository.existsByIdAndSuscripcion_Id(data.getId(), data.getSuscripcionId());
        if (!exists) {
            throw CobroNoExisteException.create();
        }
    }
}
