package com.agrosync.application.primaryports.interactor.usuarios.impl;

import com.agrosync.application.primaryports.dto.usuarios.UsuarioDTO;
import com.agrosync.application.primaryports.interactor.usuarios.RegistrarNuevoUsuarioInteractor;
import com.agrosync.application.primaryports.mapper.usuarios.UsuarioDTOMapper;
import com.agrosync.application.usecase.usuarios.usuario.RegistrarNuevoUsuario;
import com.agrosync.domain.usuarios.UsuarioDomain;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RegistrarNuevoUsuarioInteractorImpl implements RegistrarNuevoUsuarioInteractor {

    private final RegistrarNuevoUsuario registrarNuevoUsuario;

    public RegistrarNuevoUsuarioInteractorImpl(RegistrarNuevoUsuario registrarNuevoUsuario) {
        this.registrarNuevoUsuario = registrarNuevoUsuario;
    }

    @Override
    public void ejecutar(UsuarioDTO data) {
        UsuarioDomain usuarioDomain = UsuarioDTOMapper.INSTANCE.toDomain(data);
        registrarNuevoUsuario.ejecutar(usuarioDomain);
    }
}
