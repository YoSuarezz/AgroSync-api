package com.agrosync.application.usecase.auth.impl;

import com.agrosync.application.usecase.auth.RegisterAuth;
import com.agrosync.domain.auth.AuthUserDomain;
import com.agrosync.infrastructure.primaryadapters.adapter.response.auth.AuthResponse;
import com.agrosync.infrastructure.secondaryadapters.auth.service.AuthUserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class RegisterAuthImpl implements RegisterAuth {

    private final AuthUserDetailsService authUserDetailsService;

    public RegisterAuthImpl(AuthUserDetailsService authUserDetailsService) {
        this.authUserDetailsService = authUserDetailsService;
    }

    @Override
    public AuthResponse ejecutar(AuthUserDomain data) {
        return authUserDetailsService.register(data.getEmail(), data.getPassword(), data.getRol());
    }
}
