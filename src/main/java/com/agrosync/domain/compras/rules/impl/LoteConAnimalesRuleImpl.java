package com.agrosync.domain.compras.rules.impl;

import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.domain.compras.exceptions.CompraSinAnimalesException;
import com.agrosync.domain.compras.rules.LoteConAnimalesRule;
import com.agrosync.domain.lotes.LoteDomain;
import org.springframework.stereotype.Service;

@Service
public class LoteConAnimalesRuleImpl implements LoteConAnimalesRule {

    @Override
    public void validate(LoteDomain lote) {
        if (ObjectHelper.isNull(lote) ||
            ObjectHelper.isNull(lote.getAnimales()) ||
            lote.getAnimales().isEmpty()) {
            throw CompraSinAnimalesException.create();
        }
    }
}

