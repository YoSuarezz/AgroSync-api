package com.agrosync.domain.abonos.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class CuentaPagarNoDisponibleParaAbonoException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private CuentaPagarNoDisponibleParaAbonoException(final String userMessage) {
        super(userMessage, userMessage, new Exception());
    }

    public static CuentaPagarNoDisponibleParaAbonoException create() {
        var userMessage = "La cuenta por pagar no está disponible para recibir abonos (puede estar anulada o ya pagada).";
        return new CuentaPagarNoDisponibleParaAbonoException(userMessage);
    }
}
