package com.agrosync.domain.compras.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;

public class CompraSinAnimalesException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private CompraSinAnimalesException(String technicalMessage, String userMessage) {
        super(technicalMessage, userMessage);
    }

    public static CompraSinAnimalesException create() {
        var userMessage = "La compra debe incluir al menos un animal en el lote.";
        return new CompraSinAnimalesException(userMessage, userMessage);
    }
}

