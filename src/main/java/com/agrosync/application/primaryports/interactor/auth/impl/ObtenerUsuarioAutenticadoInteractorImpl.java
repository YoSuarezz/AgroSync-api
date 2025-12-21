package com.agrosync.application.primaryports.interactor.auth.impl;

import com.agrosync.application.primaryports.interactor.auth.ObtenerUsuarioAutenticadoInteractor;
import com.agrosync.application.primaryports.interactor.suscripcion.ObtenerSuscripcionPorIdInteractor;
import com.agrosync.application.usecase.auth.ObtenerUsuarioAutenticado;
import com.agrosync.infrastructure.primaryadapters.adapter.response.auth.AuthUserInfoResponse;
import org.springframework.stereotype.Service;

@Service
public class ObtenerUsuarioAutenticadoInteractorImpl implements ObtenerUsuarioAutenticadoInteractor {

    private final ObtenerUsuarioAutenticado obtenerUsuarioAutenticado;
    private final ObtenerSuscripcionPorIdInteractor obtenerSuscripcionPorIdInteractor;

    public ObtenerUsuarioAutenticadoInteractorImpl(ObtenerUsuarioAutenticado obtenerUsuarioAutenticado, ObtenerSuscripcionPorIdInteractor obtenerSuscripcionPorIdInteractor) {
        this.obtenerUsuarioAutenticado = obtenerUsuarioAutenticado;
        this.obtenerSuscripcionPorIdInteractor = obtenerSuscripcionPorIdInteractor;
    }

    @Override
    public AuthUserInfoResponse ejecutar() {
        var domain = obtenerUsuarioAutenticado.ejecutar();
        var suscripcion = obtenerSuscripcionPorIdInteractor.ejecutar(domain.getSuscripcionId());

        var response = new AuthUserInfoResponse();
        response.setUserId(domain.getId());
        response.setEmail(domain.getEmail());
        response.setRol(domain.getRol().name());
        response.setSuscripcionId(suscripcion.getId());
        response.setEstadoSuscripcionEnum(suscripcion.getEstadoSuscripcion());
        response.getMensajes().add("Usuario autenticado");
        return response;
    }
}
