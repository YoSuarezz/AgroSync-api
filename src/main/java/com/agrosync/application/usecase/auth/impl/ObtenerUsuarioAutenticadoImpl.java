package com.agrosync.application.usecase.auth.impl;

import com.agrosync.application.secondaryports.mapper.auth.AuthUserEntityMapper;
import com.agrosync.application.usecase.auth.ObtenerUsuarioAutenticado;
import com.agrosync.domain.auth.AuthUserDomain;
import com.agrosync.infrastructure.secondaryadapters.auth.service.AuthUserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class ObtenerUsuarioAutenticadoImpl implements ObtenerUsuarioAutenticado {

    private final AuthUserDetailsService authUserDetailsService;

    public ObtenerUsuarioAutenticadoImpl(AuthUserDetailsService authUserDetailsService) {
        this.authUserDetailsService = authUserDetailsService;
    }

    @Override
    public AuthUserDomain ejecutar() {
        var entity = authUserDetailsService.getLoggedUser();
        return AuthUserEntityMapper.INSTANCE.toDomain(entity);
    }
}
