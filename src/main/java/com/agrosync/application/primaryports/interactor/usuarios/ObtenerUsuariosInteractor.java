package com.agrosync.application.primaryports.interactor.usuarios;

import com.agrosync.application.primaryports.dto.usuarios.request.UsuarioPageDTO;
import com.agrosync.application.primaryports.dto.usuarios.response.ObtenerUsuarioDTO;
import com.agrosync.application.primaryports.interactor.InteractorWithReturn;
import com.agrosync.infrastructure.primaryadapters.adapter.response.PageResponse;

public interface ObtenerUsuariosInteractor extends InteractorWithReturn<UsuarioPageDTO, PageResponse<ObtenerUsuarioDTO>> {
}
