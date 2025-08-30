package com.agrosync.application.primaryports.interactor.tipousuarios.impl;

import com.agrosync.application.primaryports.dto.usuarios.TipoUsuarioDTO;
import com.agrosync.application.primaryports.interactor.tipousuarios.ObtenerTipoUsuarioInteractor;
import com.agrosync.application.primaryports.mapper.usuarios.TipoUsuarioDTOMapper;
import com.agrosync.application.usecase.usuarios.tipousuarios.ObtenerTipoUsuario;
import com.agrosync.domain.usuarios.TipoUsuarioDomain;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObtenerTipoUsuarioInteractorImpl implements ObtenerTipoUsuarioInteractor {

    private final ObtenerTipoUsuario obtenerTipoUsuario;

    public ObtenerTipoUsuarioInteractorImpl(ObtenerTipoUsuario obtenerTipoUsuario) {
        this.obtenerTipoUsuario = obtenerTipoUsuario;
    }

    @Override
    public List<TipoUsuarioDTO> ejecutar() {
        List<TipoUsuarioDomain> resultados = obtenerTipoUsuario.ejecutar();
        return TipoUsuarioDTOMapper.INSTANCE.toDTOCollection(resultados);
    }
}
