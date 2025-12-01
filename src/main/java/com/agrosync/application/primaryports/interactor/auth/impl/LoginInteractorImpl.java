package com.agrosync.application.primaryports.interactor.auth.impl;

import com.agrosync.application.primaryports.dto.auth.LoginRequest;
import com.agrosync.application.primaryports.interactor.auth.LoginInteractor;
import com.agrosync.application.usecase.auth.LoginAuth;
import com.agrosync.domain.auth.AuthUserDomain;
import com.agrosync.infrastructure.primaryadapters.adapter.response.auth.AuthResponse;
import org.springframework.stereotype.Service;

@Service
public class LoginInteractorImpl implements LoginInteractor {

    private final LoginAuth loginAuth;

    public LoginInteractorImpl(LoginAuth loginAuth) {
        this.loginAuth = loginAuth;
    }

    @Override
    public AuthResponse ejecutar(LoginRequest data) {
        AuthUserDomain domain = new AuthUserDomain();
        domain.setEmail(data.getEmail());
        domain.setPassword(data.getPassword());
        return loginAuth.ejecutar(domain);
    }
}
