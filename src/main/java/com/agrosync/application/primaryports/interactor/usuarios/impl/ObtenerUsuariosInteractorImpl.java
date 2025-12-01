package com.agrosync.application.primaryports.interactor.usuarios.impl;

import com.agrosync.application.primaryports.dto.usuarios.request.RegiserNewUserDTO;
import com.agrosync.application.primaryports.dto.usuarios.request.UsuarioRequest;
import com.agrosync.application.primaryports.interactor.usuarios.ObtenerUsuariosInteractor;
import com.agrosync.application.primaryports.mapper.usuarios.UsuarioDTOMapper;
import com.agrosync.application.usecase.usuarios.usuario.ObtenerUsuarios;
import com.agrosync.domain.usuarios.UsuarioDomain;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ObtenerUsuariosInteractorImpl implements ObtenerUsuariosInteractor {

    private final ObtenerUsuarios obtenerUsuarios;

    public ObtenerUsuariosInteractorImpl(ObtenerUsuarios obtenerUsuarios) {
        this.obtenerUsuarios = obtenerUsuarios;
    }

    @Override
    public Page<RegiserNewUserDTO> ejecutar(UsuarioRequest data) {
        Page<UsuarioDomain> resultados = obtenerUsuarios.ejecutar(data);
        return UsuarioDTOMapper.INSTANCE.toDTOCollection(resultados);
    }
}
