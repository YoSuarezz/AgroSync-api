package com.agrosync.application.primaryports.interactor.auth;

import com.agrosync.application.primaryports.dto.auth.RegisterRequest;
import com.agrosync.application.primaryports.interactor.InteractorWithReturn;
import com.agrosync.infrastructure.primaryadapters.adapter.response.auth.AuthResponse;

public interface RegisterInteractor extends InteractorWithReturn<RegisterRequest, AuthResponse> {
}
