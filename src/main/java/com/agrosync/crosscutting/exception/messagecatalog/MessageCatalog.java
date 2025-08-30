package com.agrosync.crosscutting.exception.messagecatalog;

import com.agrosync.crosscutting.exception.messagecatalog.data.CodigoMensaje;
import com.agrosync.crosscutting.exception.messagecatalog.data.Mensaje;

public interface MessageCatalog {

    void inicializar();

    String obtenerContenidoMensaje(final CodigoMensaje codigo, String... parametros);

    Mensaje obtenerMensaje(final CodigoMensaje codigo, String... parametros);
}

