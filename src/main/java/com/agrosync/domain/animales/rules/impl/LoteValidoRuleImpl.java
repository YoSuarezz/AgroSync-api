package com.agrosync.domain.animales.rules.impl;

import com.agrosync.domain.animales.exceptions.LoteNoValidoException;
import com.agrosync.domain.animales.rules.LoteValidoRule;
import com.agrosync.domain.lotes.LoteDomain;
import org.springframework.stereotype.Service;

@Service
public class LoteValidoRuleImpl implements LoteValidoRule {

    @Override
    public void validate(LoteDomain lote) {

        if (lote == null || lote.getId() == null) {
            throw LoteNoValidoException.create();
        }
    }
}
