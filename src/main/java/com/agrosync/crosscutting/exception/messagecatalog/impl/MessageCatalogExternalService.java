package com.agrosync.crosscutting.exception.messagecatalog.impl;


import com.agrosync.crosscutting.exception.custom.CrosscuttingAgroSyncException;
import com.agrosync.crosscutting.exception.messagecatalog.MessageCatalog;
import com.agrosync.crosscutting.exception.messagecatalog.MessageCatalogStrategy;
import com.agrosync.crosscutting.exception.messagecatalog.data.CodigoMensaje;
import com.agrosync.crosscutting.exception.messagecatalog.data.Mensaje;
import com.agrosync.crosscutting.helpers.ObjectHelper;

import java.util.HashMap;
import java.util.Map;

public final class MessageCatalogExternalService implements MessageCatalog {

    private final Map<String, Mensaje> mensajes = new HashMap<>();

    @Override
    public final void inicializar() {
        mensajes.clear();
        //mensajes.put(CodigoMensaje.M00007.getIdentificador(),
        new Mensaje(CodigoMensaje.M00007, "La transacción se ha completado de forma satisfactoria...");

    }


    @Override
    public final String obtenerContenidoMensaje(final CodigoMensaje codigo, final String... parametros) {
        return obtenerMensaje(codigo, parametros).getContenido();
    }

    @Override
    public final Mensaje obtenerMensaje(CodigoMensaje codigo, final String... parametros) {

        if (ObjectHelper.isNull(codigo)) {
            var mensajeUsuario = MessageCatalogStrategy.getContenidoMensaje(CodigoMensaje.M00002);
            var mensajeTecnico = MessageCatalogStrategy.getContenidoMensaje(CodigoMensaje.M00001);
            throw new CrosscuttingAgroSyncException(mensajeTecnico, mensajeUsuario);
        }

        if (codigo.isBase()) {
            var mensajeUsuario = MessageCatalogStrategy.getContenidoMensaje(CodigoMensaje.M00002);
            var mensajeTecnico = MessageCatalogStrategy.getContenidoMensaje(CodigoMensaje.M00005,
                    codigo.getIdentificador());
            throw new CrosscuttingAgroSyncException(mensajeTecnico, mensajeUsuario);
        }

        if (!mensajes.containsKey(codigo.getIdentificador())) {
            var mensajeUsuario = MessageCatalogStrategy.getContenidoMensaje(CodigoMensaje.M00002);
            var mensajeTecnico = MessageCatalogStrategy.getContenidoMensaje(CodigoMensaje.M00006,
                    codigo.getIdentificador());
            throw new CrosscuttingAgroSyncException(mensajeTecnico, mensajeUsuario);
        }

        Mensaje mensaje = mensajes.get(codigo.getIdentificador());
        String contenido = mensaje.getContenido();

        // Reemplazar los parámetros en el contenido del mensaje
        for (int i = 0; i < parametros.length; i++) {
            contenido = contenido.replace("${" + (i + 1) + "}", parametros[i]);
        }

        return new Mensaje(codigo, contenido);
    }
}