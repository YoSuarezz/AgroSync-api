package com.agrosync.application.usecase.auth;

import com.agrosync.application.usecase.UseCaseWithReturn;
import com.agrosync.domain.auth.AuthUserDomain;
import com.agrosync.infrastructure.primaryadapters.adapter.response.auth.AuthResponse;

public interface LoginAuth extends UseCaseWithReturn<AuthUserDomain, AuthResponse> {
}
