package com.sedikev.infrastructure.adapter.response.usuarios;

import com.sedikev.application.primaryports.dto.usuarios.UsuarioDTO;
import com.sedikev.infrastructure.adapter.response.Response;

import java.util.ArrayList;

public class UsuarioResponse extends Response<UsuarioDTO> {

    public UsuarioResponse() {
        setMensajes(new ArrayList<String>());
        setDatos(new ArrayList<>());
    }

}
