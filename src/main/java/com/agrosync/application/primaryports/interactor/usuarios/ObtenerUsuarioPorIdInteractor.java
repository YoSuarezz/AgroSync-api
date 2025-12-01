package com.agrosync.application.primaryports.interactor.usuarios;

import com.agrosync.application.primaryports.dto.usuarios.response.ObtenerUsuarioDTO;
import com.agrosync.application.primaryports.interactor.InteractorWithReturn;

import java.util.UUID;

public interface ObtenerUsuarioPorIdInteractor extends InteractorWithReturn<UUID, ObtenerUsuarioDTO> {
}
