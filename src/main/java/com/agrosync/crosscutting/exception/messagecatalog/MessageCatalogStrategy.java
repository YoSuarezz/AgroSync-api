package com.agrosync.crosscutting.exception.messagecatalog;


import com.agrosync.crosscutting.exception.custom.CrosscuttingAgroSyncException;
import com.agrosync.crosscutting.exception.messagecatalog.data.CodigoMensaje;
import com.agrosync.crosscutting.exception.messagecatalog.data.Mensaje;
import com.agrosync.crosscutting.exception.messagecatalog.impl.MessageCatalogBase;
import com.agrosync.crosscutting.exception.messagecatalog.impl.MessageCatalogExternalService;
import com.agrosync.crosscutting.helpers.ObjectHelper;

public final class MessageCatalogStrategy {

    private static final MessageCatalog base = new MessageCatalogBase();
    private static final MessageCatalog externalService = new MessageCatalogExternalService();

    static {
        inicializar();
    }

    private MessageCatalogStrategy() {
        super();
    }

    public static void inicializar() {
        base.inicializar();
        externalService.inicializar();
    }

    private static final MessageCatalog getStrategy(final boolean isBase) {
        return isBase ? base : externalService;
    }

    public static final Mensaje getMensaje(final CodigoMensaje codigo, final String... parametros) {

        if (ObjectHelper.getObjectHelper().isNull(codigo)) {
            var mensajeUsuario = MessageCatalogStrategy.getContenidoMensaje(CodigoMensaje.M00002);
            var mensajeTecnico = MessageCatalogStrategy.getContenidoMensaje(CodigoMensaje.M00001);
            throw new CrosscuttingAgroSyncException(mensajeTecnico, mensajeUsuario);
        }

        return getStrategy(codigo.isBase()).obtenerMensaje(codigo, parametros);
    }

    public static final String getContenidoMensaje(final CodigoMensaje codigo, final String... parametros) {
        return getMensaje(codigo, parametros).getContenido();
    }

}

