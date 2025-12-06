package com.agrosync.domain.lotes.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class IdentificadorLoteNoExisteException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private IdentificadorLoteNoExisteException(final String userMessage) {
        super(userMessage, userMessage, new Exception());
    }

    public static IdentificadorLoteNoExisteException create() {
        var userMessage = "El identificador del Lote proporcionado no existe en el sistema.";
        return new IdentificadorLoteNoExisteException(userMessage);
    }
}
