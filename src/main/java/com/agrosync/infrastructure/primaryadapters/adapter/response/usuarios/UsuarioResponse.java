package com.agrosync.infrastructure.primaryadapters.adapter.response.usuarios;

import com.agrosync.application.primaryports.dto.usuarios.request.RegiserNewUserDTO;
import com.agrosync.infrastructure.primaryadapters.adapter.response.Response;

import java.util.ArrayList;

public class UsuarioResponse extends Response<RegiserNewUserDTO> {

    public UsuarioResponse() {
        setMensajes(new ArrayList<String>());
        setDatos(new ArrayList<>());
    }

}
