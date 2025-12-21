package com.agrosync.crosscutting.exception.messagecatalog.impl;

import com.agrosync.crosscutting.exception.custom.CrosscuttingAgroSyncException;
import com.agrosync.crosscutting.exception.messagecatalog.MessageCatalog;
import com.agrosync.crosscutting.exception.messagecatalog.data.CodigoMensaje;
import com.agrosync.crosscutting.exception.messagecatalog.data.Mensaje;
import com.agrosync.crosscutting.helpers.ObjectHelper;

import java.util.HashMap;
import java.util.Map;

public final class MessageCatalogBase implements MessageCatalog {

    private final Map<String, Mensaje> mensajes = new HashMap<>();

    @Override
    public final void inicializar() {
        mensajes.clear();
        mensajes.put(CodigoMensaje.M00001.getIdentificador(), new Mensaje(CodigoMensaje.M00001,
                "El código del mensaje que quiere obtener del catálogo mensajes llegó nulo..."));
        mensajes.put(CodigoMensaje.M00002.getIdentificador(), new Mensaje(CodigoMensaje.M00002,
                "Se ha presentado un problema tratando de llevar a cabo la operación deseada..."));
        mensajes.put(CodigoMensaje.M00003.getIdentificador(), new Mensaje(CodigoMensaje.M00003,
                "El identificador del mensaje \"${1}\" que se intentó obtener, no está en el catálogo de mensajes base..."));
        mensajes.put(CodigoMensaje.M00004.getIdentificador(), new Mensaje(CodigoMensaje.M00004,
                "El mensaje con identificador \"${1}\" que se intentó obtener, no está configurado para residir en el catálogo de mensajes base..."));
        mensajes.put(CodigoMensaje.M00005.getIdentificador(), new Mensaje(CodigoMensaje.M00005,
                "El mensaje con identificador \"${1}\" que se intentó obtener, no está configurado para residir en el catálogo de mensajes externo..."));
        mensajes.put(CodigoMensaje.M00006.getIdentificador(), new Mensaje(CodigoMensaje.M00006,
                "El identificador del mensaje \"${1}\" que se intentó obtener, no está en el catálogo de mensajes externo..."));
        mensajes.put(CodigoMensaje.M00007.getIdentificador(), new Mensaje(CodigoMensaje.M00007,
                "Se ha presentado un problrma tratando de validar si la conexion SQL con la fuente de informacion deseada estaba cerrada..."));
    }
    @Override
    public final String obtenerContenidoMensaje(final CodigoMensaje codigo, final String... parametros) {
        return obtenerMensaje(codigo, parametros).getContenido();
    }

    @Override
    public final Mensaje obtenerMensaje(final CodigoMensaje codigo, final String... parametros) {

        if (ObjectHelper.isNull(codigo)) {
            var mensajeUsuario = obtenerContenidoMensaje(CodigoMensaje.M00002);
            var mensajeTecnico = obtenerContenidoMensaje(CodigoMensaje.M00001);
            throw new CrosscuttingAgroSyncException(mensajeTecnico, mensajeUsuario);
        }

        if (!codigo.isBase()) {
            var mensajeUsuario = obtenerContenidoMensaje(CodigoMensaje.M00002);
            var mensajeTecnico = obtenerContenidoMensaje(CodigoMensaje.M00004, codigo.getIdentificador());
            throw new CrosscuttingAgroSyncException(mensajeTecnico, mensajeUsuario);
        }

        if (!mensajes.containsKey(codigo.getIdentificador())) {
            var mensajeUsuario = obtenerContenidoMensaje(CodigoMensaje.M00002);
            var mensajeTecnico = obtenerContenidoMensaje(CodigoMensaje.M00003, codigo.getIdentificador());
            throw new CrosscuttingAgroSyncException(mensajeTecnico, mensajeUsuario);
        }

        Mensaje mensaje = mensajes.get(codigo.getIdentificador());
        String contenido = mensaje.getContenido();

        // Reemplazar los parámetros en el contenido del mensaje
        for (int i = 0; i < parametros.length; i++) {
            contenido = contenido.replace("${" + (i + 1) + "}", parametros[i]);
        }


        return mensajes.get(codigo.getIdentificador());
    }

}
