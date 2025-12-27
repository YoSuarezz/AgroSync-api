package com.agrosync.application.primaryports.interactor.usuarios.impl;

import com.agrosync.application.primaryports.dto.usuarios.request.UsuarioDetalladoFiltroDTO;
import com.agrosync.application.primaryports.dto.usuarios.response.ObtenerUsuarioDetalladoDTO;
import com.agrosync.application.primaryports.interactor.usuarios.ObtenerUsuarioDetalladoInteractor;
import com.agrosync.application.primaryports.mapper.usuarios.UsuarioDTOMapper;
import com.agrosync.application.usecase.usuarios.ObtenerUsuarioDetallado;
import com.agrosync.domain.usuarios.UsuarioDetalladoDomain;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ObtenerUsuarioDetalladoInteractorImpl implements ObtenerUsuarioDetalladoInteractor {

    private final ObtenerUsuarioDetallado obtenerUsuarioDetallado;

    public ObtenerUsuarioDetalladoInteractorImpl(ObtenerUsuarioDetallado obtenerUsuarioDetallado) {
        this.obtenerUsuarioDetallado = obtenerUsuarioDetallado;
    }

    @Override
    public ObtenerUsuarioDetalladoDTO ejecutar(UsuarioDetalladoFiltroDTO data) {
        UsuarioDetalladoDomain usuario = obtenerUsuarioDetallado.ejecutar(data);
        return UsuarioDTOMapper.INSTANCE.toObtenerDetalleDTO(usuario);
    }
}
