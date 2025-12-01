package com.agrosync.application.usecase.auth.impl;

import com.agrosync.application.usecase.auth.LoginAuth;
import com.agrosync.domain.auth.AuthUserDomain;
import com.agrosync.infrastructure.primaryadapters.adapter.response.auth.AuthResponse;
import com.agrosync.infrastructure.secondaryadapters.auth.service.AuthUserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class LoginAuthImpl implements LoginAuth {

    private final AuthUserDetailsService authUserDetailsService;

    public LoginAuthImpl(AuthUserDetailsService authUserDetailsService) {
        this.authUserDetailsService = authUserDetailsService;
    }

    @Override
    public AuthResponse ejecutar(AuthUserDomain data) {
        return authUserDetailsService.login(data.getEmail(), data.getPassword());
    }
}
