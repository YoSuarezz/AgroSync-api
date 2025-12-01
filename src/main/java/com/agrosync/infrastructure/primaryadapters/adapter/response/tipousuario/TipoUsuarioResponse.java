package com.agrosync.infrastructure.primaryadapters.adapter.response.tipousuario;

import com.agrosync.application.primaryports.dto.usuarios.request.RegiserNewUserDTO;
import com.agrosync.infrastructure.primaryadapters.adapter.response.Response;

import java.util.ArrayList;

public class TipoUsuarioResponse extends Response<RegiserNewUserDTO> {

    public TipoUsuarioResponse() {
        setMensajes(new ArrayList<String>());
        setDatos(new ArrayList<>());
    }
}
