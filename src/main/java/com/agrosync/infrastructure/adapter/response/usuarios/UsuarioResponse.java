package com.agrosync.infrastructure.adapter.response.usuarios;

import com.agrosync.application.primaryports.dto.usuarios.UsuarioDTO;
import com.agrosync.infrastructure.adapter.response.Response;

import java.util.ArrayList;

public class UsuarioResponse extends Response<UsuarioDTO> {

    public UsuarioResponse() {
        setMensajes(new ArrayList<String>());
        setDatos(new ArrayList<>());
    }

}
