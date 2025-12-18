package com.agrosync.domain.cobros.rules.impl;

import com.agrosync.domain.enums.cuentas.EstadoCuentaEnum;
import com.agrosync.domain.cobros.exceptions.CuentaCobrarNoDisponibleParaCobroException;
import com.agrosync.domain.cobros.rules.CuentaCobrarDisponibleParaCobroRule;
import org.springframework.stereotype.Service;

@Service
public class CuentaCobrarDisponibleParaCobroRuleImpl implements CuentaCobrarDisponibleParaCobroRule {

    @Override
    public void validate(EstadoCuentaEnum estado) {
        if (estado == EstadoCuentaEnum.ANULADA || estado == EstadoCuentaEnum.COBRADA) {
            throw CuentaCobrarNoDisponibleParaCobroException.create();
        }
    }
}
