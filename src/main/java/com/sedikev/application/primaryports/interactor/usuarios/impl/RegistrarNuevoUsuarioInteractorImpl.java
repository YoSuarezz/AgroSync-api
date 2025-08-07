package com.sedikev.application.primaryports.interactor.usuarios.impl;

import com.sedikev.application.primaryports.dto.usuarios.UsuarioDTO;
import com.sedikev.application.primaryports.interactor.usuarios.RegistrarNuevoUsuarioInteractor;
import com.sedikev.application.primaryports.mapper.usuarios.UsuarioDTOMapper;
import com.sedikev.application.usecase.usuarios.RegistrarNuevoUsuario;
import com.sedikev.domain.usuarios.UsuarioDomain;
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
