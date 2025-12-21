package com.agrosync.domain.ventas.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class VentaYaAnuladaException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private VentaYaAnuladaException(String technicalMessage, String userMessage) {
        super(technicalMessage, userMessage);
    }

    public static VentaYaAnuladaException create() {
        var userMessage = "La venta ya se encuentra anulada y no puede ser anulada nuevamente.";
        return new VentaYaAnuladaException(userMessage, userMessage);
    }
}

