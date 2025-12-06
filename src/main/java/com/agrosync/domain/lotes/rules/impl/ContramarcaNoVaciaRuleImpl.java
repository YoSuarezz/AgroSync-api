package com.agrosync.domain.lotes.rules.impl;

import com.agrosync.domain.lotes.exceptions.ContramarcaVaciaException;
import com.agrosync.domain.lotes.rules.ContramarcaNoVaciaRule;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ContramarcaNoVaciaRuleImpl implements ContramarcaNoVaciaRule {

    @Override
    public void validate(String contramarca) {
        if (!StringUtils.hasText(contramarca)) {
            throw ContramarcaVaciaException.create();
        }
    }
}