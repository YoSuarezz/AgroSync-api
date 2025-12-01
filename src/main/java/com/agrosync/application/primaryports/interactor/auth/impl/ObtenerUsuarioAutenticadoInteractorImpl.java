package com.agrosync.application.primaryports.interactor.auth.impl;

import com.agrosync.application.primaryports.interactor.auth.ObtenerUsuarioAutenticadoInteractor;
import com.agrosync.application.usecase.auth.ObtenerUsuarioAutenticado;
import com.agrosync.infrastructure.primaryadapters.adapter.response.auth.AuthUserInfoResponse;
import org.springframework.stereotype.Service;

@Service
public class ObtenerUsuarioAutenticadoInteractorImpl implements ObtenerUsuarioAutenticadoInteractor {

    private final ObtenerUsuarioAutenticado obtenerUsuarioAutenticado;

    public ObtenerUsuarioAutenticadoInteractorImpl(ObtenerUsuarioAutenticado obtenerUsuarioAutenticado) {
        this.obtenerUsuarioAutenticado = obtenerUsuarioAutenticado;
    }

    @Override
    public AuthUserInfoResponse ejecutar() {
        var domain = obtenerUsuarioAutenticado.ejecutar();
        var response = new AuthUserInfoResponse();
        response.setId(domain.getId());
        response.setEmail(domain.getEmail());
        response.setRol(domain.getRol().name());
        response.getMensajes().add("Usuario autenticado");
        return response;
    }
}
