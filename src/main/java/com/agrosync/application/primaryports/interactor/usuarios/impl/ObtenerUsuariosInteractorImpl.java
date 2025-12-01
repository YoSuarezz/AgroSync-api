package com.agrosync.application.primaryports.interactor.usuarios.impl;

import com.agrosync.application.primaryports.dto.usuarios.request.UsuarioPageDTO;
import com.agrosync.application.primaryports.dto.usuarios.response.ObtenerUsuarioDTO;
import com.agrosync.application.primaryports.interactor.usuarios.ObtenerUsuariosInteractor;
import com.agrosync.application.primaryports.mapper.usuarios.UsuarioDTOMapper;
import com.agrosync.application.usecase.usuarios.ObtenerUsuarios;
import com.agrosync.domain.usuarios.UsuarioDomain;
import com.agrosync.infrastructure.primaryadapters.adapter.response.PageResponse;
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
    public PageResponse<ObtenerUsuarioDTO> ejecutar(UsuarioPageDTO data) {
        Page<UsuarioDomain> resultados = obtenerUsuarios.ejecutar(data);
        Page<ObtenerUsuarioDTO> dtoPage = UsuarioDTOMapper.INSTANCE.toObtenerDTOCollection(resultados);
        return PageResponse.from(dtoPage);
    }
}
