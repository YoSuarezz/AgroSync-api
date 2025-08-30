package com.agrosync.infrastructure.primaryadapters.adapter.response.tipousuario;

import com.agrosync.application.primaryports.dto.usuarios.UsuarioDTO;
import com.agrosync.infrastructure.primaryadapters.adapter.response.Response;

import java.util.ArrayList;

public class TipoUsuarioResponse extends Response<UsuarioDTO> {

    public TipoUsuarioResponse() {
        setMensajes(new ArrayList<String>());
        setDatos(new ArrayList<>());
    }
}
