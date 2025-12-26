package com.agrosync.domain.lotes.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class LoteNoEditableException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private LoteNoEditableException(final String userMessage) {
        super(userMessage, userMessage, new Exception());
    }

    public static LoteNoEditableException create() {
        var userMessage = "El lote no puede ser editado porque la compra fue anulada";
        return new LoteNoEditableException(userMessage);
    }
}
