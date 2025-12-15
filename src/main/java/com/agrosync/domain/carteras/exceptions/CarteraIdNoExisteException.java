package com.agrosync.domain.carteras.exceptions;

import com.agrosync.crosscutting.exception.custom.RuleAgroSyncException;
import com.agrosync.domain.usuarios.exceptions.UsuarioIdNoExisteException;

public class CarteraIdNoExisteException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private CarteraIdNoExisteException(final String userMessage) {
        super(userMessage, userMessage, new Exception());
    }

    public static CarteraIdNoExisteException create() {
        var userMessage = "La cartera no existe";
        return new CarteraIdNoExisteException(userMessage);
    }
}
