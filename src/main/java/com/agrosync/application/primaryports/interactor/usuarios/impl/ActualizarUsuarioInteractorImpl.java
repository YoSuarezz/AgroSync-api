package com.agrosync.application.primaryports.interactor.usuarios.impl;

import com.agrosync.application.primaryports.dto.usuarios.request.RegiserNewUserDTO;
import com.agrosync.application.primaryports.interactor.usuarios.ActualizarUsuarioInteractor;
import com.agrosync.application.primaryports.mapper.usuarios.UsuarioDTOMapper;
import com.agrosync.application.usecase.usuarios.usuario.ActualizarUsuario;
import com.agrosync.domain.usuarios.UsuarioDomain;
import org.springframework.stereotype.Service;

@Service
public class ActualizarUsuarioInteractorImpl implements ActualizarUsuarioInteractor {

    private final ActualizarUsuario actualizarUsuario;

    public ActualizarUsuarioInteractorImpl(ActualizarUsuario actualizarUsuario) {
        this.actualizarUsuario = actualizarUsuario;
    }

    @Override
    public void ejecutar(RegiserNewUserDTO data) {
        UsuarioDomain usuarioDomain = UsuarioDTOMapper.INSTANCE.toDomain(data);
        actualizarUsuario.ejecutar(usuarioDomain);
    }
}
