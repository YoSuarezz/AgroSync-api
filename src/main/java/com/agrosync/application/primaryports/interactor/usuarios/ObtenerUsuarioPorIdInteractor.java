package com.agrosync.application.primaryports.interactor.usuarios;

import com.agrosync.application.primaryports.dto.usuarios.request.RegiserNewUserDTO;
import com.agrosync.application.primaryports.interactor.InteractorWithReturn;

import java.util.UUID;

public interface ObtenerUsuarioPorIdInteractor extends InteractorWithReturn<UUID, RegiserNewUserDTO> {
}
