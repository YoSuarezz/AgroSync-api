package com.agrosync.domain.cobros.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class CuentaCobrarNoDisponibleParaCobroException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private CuentaCobrarNoDisponibleParaCobroException(final String userMessage) {
        super(userMessage, userMessage, new Exception());
    }

    public static CuentaCobrarNoDisponibleParaCobroException create() {
        var userMessage = "La cuenta por cobrar no está disponible para recibir cobros (puede estar anulada o ya cobrada).";
        return new CuentaCobrarNoDisponibleParaCobroException(userMessage);
    }
}
