package com.agrosync.application.primaryports.interactor.auth.impl;

import com.agrosync.application.primaryports.dto.auth.RegisterRequest;
import com.agrosync.application.primaryports.interactor.auth.RegisterInteractor;
import com.agrosync.application.usecase.auth.RegisterAuth;
import com.agrosync.domain.auth.AuthUserDomain;
import com.agrosync.infrastructure.primaryadapters.adapter.response.auth.AuthResponse;
import org.springframework.stereotype.Service;

@Service
public class RegisterInteractorImpl implements RegisterInteractor {

    private final RegisterAuth registerAuth;

    public RegisterInteractorImpl(RegisterAuth registerAuth) {
        this.registerAuth = registerAuth;
    }

    @Override
    public AuthResponse ejecutar(RegisterRequest data) {
        AuthUserDomain domain = new AuthUserDomain();
        domain.setEmail(data.getEmail());
        domain.setPassword(data.getPassword());
        domain.setRol(data.getRol());
        return registerAuth.ejecutar(domain);
    }
}
