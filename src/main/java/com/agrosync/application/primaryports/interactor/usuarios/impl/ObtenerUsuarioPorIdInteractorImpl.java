package com.agrosync.application.primaryports.interactor.usuarios.impl;

import com.agrosync.application.primaryports.dto.usuarios.request.UsuarioIdSuscripcionDTO;
import com.agrosync.application.primaryports.dto.usuarios.response.ObtenerUsuarioDTO;
import com.agrosync.application.primaryports.interactor.usuarios.ObtenerUsuarioPorIdInteractor;
import com.agrosync.application.primaryports.mapper.usuarios.UsuarioDTOMapper;
import com.agrosync.application.usecase.usuarios.ObtenerUsuarioPorId;
import com.agrosync.domain.usuarios.UsuarioDomain;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ObtenerUsuarioPorIdInteractorImpl implements ObtenerUsuarioPorIdInteractor {

    private final ObtenerUsuarioPorId obtenerUsuarioPorId;

    public ObtenerUsuarioPorIdInteractorImpl(ObtenerUsuarioPorId obtenerUsuarioPorId) {
        this.obtenerUsuarioPorId = obtenerUsuarioPorId;
    }

    @Override
    public ObtenerUsuarioDTO ejecutar(UsuarioIdSuscripcionDTO data) {
        UsuarioDomain resultado = obtenerUsuarioPorId.ejecutar(data);
        return UsuarioDTOMapper.INSTANCE.toObtenerDTO(resultado);
    }
}
