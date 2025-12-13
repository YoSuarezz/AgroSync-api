package com.agrosync.domain.abonos.rules.impl;

import com.agrosync.application.primaryports.dto.abonos.request.AbonoIdSuscripcionDTO;
import com.agrosync.application.secondaryports.repository.AbonoRepository;
import com.agrosync.domain.abonos.exceptions.AbonoNoExisteException;
import com.agrosync.domain.abonos.rules.AbonoExisteRule;
import org.springframework.stereotype.Service;

@Service
public class AbonoExisteRuleImpl implements AbonoExisteRule {

    private final AbonoRepository abonoRepository;

    public AbonoExisteRuleImpl(AbonoRepository abonoRepository) {
        this.abonoRepository = abonoRepository;
    }

    @Override
    public void validate(AbonoIdSuscripcionDTO data) {
        boolean exists = abonoRepository.existsByIdAndSuscripcion_Id(data.getId(), data.getSuscripcionId());
        if (!exists) {
            throw AbonoNoExisteException.create();
        }
    }
}
