package com.agrosync.domain.abonos.rules.impl;

import com.agrosync.domain.enums.cuentas.EstadoCuentaEnum;
import com.agrosync.domain.abonos.exceptions.CuentaPagarNoDisponibleParaAbonoException;
import com.agrosync.domain.abonos.rules.CuentaPagarDisponibleParaAbonoRule;
import org.springframework.stereotype.Service;

@Service
public class CuentaPagarDisponibleParaAbonoRuleImpl implements CuentaPagarDisponibleParaAbonoRule {

    @Override
    public void validate(EstadoCuentaEnum estado) {
        if (estado == EstadoCuentaEnum.ANULADA || estado == EstadoCuentaEnum.PAGADA) {
            throw CuentaPagarNoDisponibleParaAbonoException.create();
        }
    }
}
