package com.agrosync.application.primaryports.interactor.carteras.impl;

import com.agrosync.application.primaryports.dto.carteras.request.CarteraIdSuscripcionDTO;
import com.agrosync.application.primaryports.dto.carteras.response.ObtenerCarteraDTO;
import com.agrosync.application.primaryports.dto.usuarios.request.UsuarioIdSuscripcionDTO;
import com.agrosync.application.primaryports.dto.usuarios.response.ObtenerUsuarioDTO;
import com.agrosync.application.primaryports.interactor.carteras.ObtenerCarteraPorIdInteractor;
import com.agrosync.application.primaryports.mapper.carteras.CarteraDTOMapper;
import com.agrosync.application.primaryports.mapper.usuarios.UsuarioDTOMapper;
import com.agrosync.application.usecase.carteras.ObtenerCarteraPorId;
import com.agrosync.application.usecase.usuarios.ObtenerUsuarioPorId;
import com.agrosync.domain.carteras.CarteraDomain;
import com.agrosync.domain.usuarios.UsuarioDomain;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ObtenerCarteraPorIdInteractorImpl implements ObtenerCarteraPorIdInteractor {

    private final ObtenerCarteraPorId obtenerCarteraPorId;

    public ObtenerCarteraPorIdInteractorImpl(ObtenerCarteraPorId obtenerCarteraPorId) {
        this.obtenerCarteraPorId = obtenerCarteraPorId;
    }

    @Override
    public ObtenerCarteraDTO ejecutar(CarteraIdSuscripcionDTO data) {
        CarteraDomain resultado = obtenerCarteraPorId.ejecutar(data);
        return CarteraDTOMapper.INSTANCE.toObtenerDTO(resultado);
    }
}
