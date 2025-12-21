package com.agrosync.domain.compras.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class CompraConAnimalesVendidosException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private CompraConAnimalesVendidosException(String technicalMessage, String userMessage) {
        super(technicalMessage, userMessage);
    }

    public static CompraConAnimalesVendidosException create() {
        var userMessage = "No se puede anular la compra porque algunos animales del lote ya fueron vendidos.";
        return new CompraConAnimalesVendidosException(userMessage, userMessage);
    }
}

