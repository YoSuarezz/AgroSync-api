package com.agrosync.application.primaryports.interactor.usuarios.impl;

import com.agrosync.application.primaryports.dto.usuarios.request.RegiserNewUserDTO;
import com.agrosync.application.primaryports.interactor.usuarios.ObtenerUsuarioPorIdInteractor;
import com.agrosync.application.primaryports.mapper.usuarios.UsuarioDTOMapper;
import com.agrosync.application.usecase.usuarios.usuario.ObtenerUsuarioPorId;
import com.agrosync.domain.usuarios.UsuarioDomain;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class ObtenerUsuarioPorIdInteractorImpl implements ObtenerUsuarioPorIdInteractor {

    private final ObtenerUsuarioPorId obtenerUsuarioPorId;

    public ObtenerUsuarioPorIdInteractorImpl(ObtenerUsuarioPorId obtenerUsuarioPorId) {
        this.obtenerUsuarioPorId = obtenerUsuarioPorId;
    }

    @Override
    public RegiserNewUserDTO ejecutar(UUID data) {
        UsuarioDomain resultado = obtenerUsuarioPorId.ejecutar(data);
        return UsuarioDTOMapper.INSTANCE.toDTO(resultado);
    }
}
